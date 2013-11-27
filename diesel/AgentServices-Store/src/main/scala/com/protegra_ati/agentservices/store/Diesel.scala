// -*- mode: Scala;-*- 
// Filename:    Diesel.scala 
// Authors:     lgm                                                    
// Creation:    Sat Apr 27 00:25:52 2013 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.evaluator.distribution

import com.biosimilarity.evaluator.dsl._

import com.protegra_ati.agentservices.store._

import com.protegra_ati.agentservices.store.extensions.URIExtensions._
//import com.protegra_ati.agentservices.store.extensions.URMExtensions._
import com.protegra_ati.agentservices.store.extensions.MonikerExtensions._

import com.biosimilarity.lift.model.ApplicationDefaults
import com.biosimilarity.lift.model.store.xml._
import com.biosimilarity.lift.model.store._
import com.biosimilarity.lift.model.agent._
import com.biosimilarity.lift.model.msg._
import com.biosimilarity.lift.lib._
import com.biosimilarity.lift.lib.moniker._
import net.liftweb.amqp._

import scala.util.continuations._ 
import scala.concurrent.{Channel => Chan, _}
//import scala.concurrent.cpsops._
import scala.xml._
import scala.collection.mutable.Map
import scala.collection.mutable.MapProxy
import scala.collection.mutable.HashMap
import scala.collection.mutable.LinkedHashMap
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Stack
import scala.collection.mutable.MutableList

import com.rabbitmq.client._

import org.prolog4j._

import com.mongodb.casbah.Imports._

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver

import biz.source_code.base64Coder.Base64Coder

import javax.xml.transform.OutputKeys

import java.util.UUID
import java.net.URI
import java.util.Properties
import java.io.File
import java.io.FileInputStream
import java.io.OutputStreamWriter
import java.io.ObjectInputStream
import java.io.ByteArrayInputStream
import java.io.ObjectOutputStream
import java.io.ByteArrayOutputStream

package diesel {
  import scala.xml._
  import scala.xml.XML._
  import scala.collection.mutable.Buffer
  import scala.collection.mutable.ListBuffer

  object DieselEngineScope
         extends AgentKVDBMongoNodeScope[String,String,String,ConcreteHL.HLExpr]
         with UUIDOps
         with Serializable
  {
    import SpecialKURIDefaults._
    import identityConversions._

    type ACTypes = AgentCnxnTypes
    object TheACT extends ACTypes
    override def protoAgentCnxnTypes : ACTypes = TheACT

    type MTTypes = MonadicTermTypes[String,String,String,ConcreteHL.HLExpr]
    object TheMTT extends MTTypes with Serializable
    override def protoTermTypes : MTTypes = TheMTT

    type DATypes = DistributedAskTypes
    object TheDAT extends DATypes with Serializable
    override def protoAskTypes : DATypes = TheDAT
    
    override type MsgTypes = DTSMSHRsrc   
    override type RsrcMsgTypes = DTSMSHRsrc   
    
    @transient
    val protoDreqUUID = getUUID()
    @transient
    val protoDrspUUID = getUUID()    

    @transient
    lazy val aLabel = new CnxnCtxtLeaf[String,String,String]( Left( "a" ) )

    object MonadicDRsrcMsgs extends RsrcMsgTypes with Serializable {
      
      @transient
      override def protoDreq : DReq = MDGetRequest( aLabel )
      @transient
      override def protoDrsp : DRsp = MDGetResponse( aLabel, ConcreteHL.Bottom )
      @transient
      override def protoJtsreq : JTSReq =
        JustifiedRequest(
          protoDreqUUID,
          new URI( "agent", protoDreqUUID.toString, "/invitation", "" ),
          new URI( "agent", protoDreqUUID.toString, "/invitation", "" ),
          getUUID(),
          protoDreq,
          None
        )
      @transient
      override def protoJtsrsp : JTSRsp = 
        JustifiedResponse(
          protoDreqUUID,
          new URI( "agent", protoDrspUUID.toString, "/invitation", "" ),
          new URI( "agent", protoDrspUUID.toString, "/invitation", "" ),
          getUUID(),
          protoDrsp,
          None
        )
      override def protoJtsreqorrsp : JTSReqOrRsp =
        Left( protoJtsreq )
    }
    
    override def protoMsgs : MsgTypes = MonadicDRsrcMsgs
    override def protoRsrcMsgs : RsrcMsgTypes = MonadicDRsrcMsgs

    object Being extends AgentPersistenceScope with Serializable {      
      override type EMTypes = ExcludedMiddleTypes[mTT.GetRequest,mTT.GetRequest,mTT.Resource]
      object theEMTypes extends ExcludedMiddleTypes[mTT.GetRequest,mTT.GetRequest,mTT.Resource]
       with Serializable
      {
        case class PrologSubstitution( soln : LinkedHashMap[String,CnxnCtxtLabel[String,String,String]] )
           extends Function1[mTT.Resource,Option[mTT.Resource]] {
             override def apply( rsrc : mTT.Resource ) = {
               Some( mTT.RBoundHM( Some( rsrc ), Some( soln ) ) )
             }
           }
        override type Substitution = PrologSubstitution 
      }      

      override def protoEMTypes : EMTypes =
        theEMTypes

      object AgentKVDBNodeFactory
             extends BaseAgentKVDBNodeFactoryT
             with AgentKVDBNodeFactoryT
             with WireTap
             with Serializable {                       
        type AgentCache[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse] = AgentKVDB[ReqBody,RspBody]
        //type AgentNode[Rq <: PersistedKVDBNodeRequest, Rs <: PersistedKVDBNodeResponse] = AgentKVDBNode[Rq,Rs]

        override def tap [A] ( fact : A ) : Unit = {
          BasicLogService.reportage( fact )
        }

        override def mkCache[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse]( 
          here : URI,
          configFileName : Option[String]
        ) : AgentCache[ReqBody,RspBody] = {
          new AgentKVDB[ReqBody, RspBody](
            MURI( here ),
            configFileName
          ) with Blobify with AMQPMonikerOps {          
            override def toXQSafeJSONBlob( x : java.lang.Object ) : String = {
              new XStream( new JettisonMappedXmlDriver() ).toXML( x )
            }
            override def fromXQSafeJSONBlob( blob : String ) : java.lang.Object = {              
              new XStream( new JettisonMappedXmlDriver() ).fromXML( blob )
            }      
            class StringMongoDBManifest(
              override val storeUnitStr : String,
              @transient override val labelToNS : Option[String => String],
              @transient override val textToVar : Option[String => String],
              @transient override val textToTag : Option[String => String]
            )
            extends MongoDBManifest( /* database */ ) {
              override def valueStorageType : String = {
                throw new Exception( "valueStorageType not overriden in instantiation" )
              }
              override def continuationStorageType : String = {
                throw new Exception( "continuationStorageType not overriden in instantiation" )
              }
              
              override def storeUnitStr[Src,Label,Trgt]( cnxn : Cnxn[Src,Label,Trgt] ) : String = {     
                cnxn match {
                  case CCnxn( s, l, t ) => s.toString + l.toString + t.toString
                  case acT.AgentCnxn( s, l, t ) => s.getHost + l.toString + t.getHost
                }           
              } 
              
              def kvNameSpace : String = "record"
              def kvKNameSpace : String = "kRecord"
              
              def compareNameSpace( ns1 : String, ns2 : String ) : Boolean = {
                ns1.equals( ns2 )
              }
              
              override def asStoreValue(
                rsrc : mTT.Resource
              ) : CnxnCtxtLeaf[String,String,String] with Factual = {
                BasicLogService.tweet(
                  "In asStoreValue on " + this + " for resource: " + rsrc
                )
                val storageDispatch = 
                  rsrc match {
                    case k : mTT.Continuation => {
                      BasicLogService.tweet(
                        "Resource " + rsrc + " is a continuation"
                      )
                      continuationStorageType
                    }
                    case _ => {
                      BasicLogService.tweet(
                        "Resource " + rsrc + " is a value"
                      )
                      valueStorageType
                    }
                  };
                
                BasicLogService.tweet(
                  "storageDispatch: " + storageDispatch
                )
                
                val blob =
                  storageDispatch match {
                    case "Base64" => {
                      val baos : ByteArrayOutputStream = new ByteArrayOutputStream()
                      val oos : ObjectOutputStream = new ObjectOutputStream( baos )
                      oos.writeObject( rsrc.asInstanceOf[Serializable] )
                      oos.close()
                      new String( Base64Coder.encode( baos.toByteArray() ) )
                    }
                    case "CnxnCtxtLabel" => {
                      BasicLogService.tweet(
                        "warning: CnxnCtxtLabel method is using XStream"
                      )
                      toXQSafeJSONBlob( rsrc )                            
                    }
                    case "XStream" => {
                      BasicLogService.tweet(
                        "using XStream method"
                      )
                      
                      toXQSafeJSONBlob( rsrc )
                    }
                    case _ => {
                      throw new Exception( "unexpected value storage type" )
                    }
                  }
                new CnxnCtxtLeaf[String,String,String](
                  Left[String,String]( blob )
                )
              }
              
              def asCacheValue(
                ccl : CnxnCtxtLabel[String,String,String]
              ) : ConcreteHL.HLExpr = {
                BasicLogService.tweet(
                  "converting to cache value"
                )
                ccl match {
                  case CnxnCtxtBranch(
                    "string",
                    CnxnCtxtLeaf( Left( rv ) ) :: Nil
                  ) => {
                    val unBlob =
                      fromXQSafeJSONBlob( rv )
                    
                    unBlob match {
                      case rsrc : mTT.Resource => {
                        getGV( rsrc ).getOrElse( ConcreteHL.Bottom )
                      }
                    }
                  }
                  case _ => {
                    //asPatternString( ccl )
                    throw new Exception( "unexpected value form: " + ccl )
                  }
                }
              }
              
              override def asResource(
                key : mTT.GetRequest, // must have the pattern to determine bindings
                value : DBObject
              ) : emT.PlaceInstance = {
                val ltns =
                  labelToNS.getOrElse(
                    throw new Exception( "must have labelToNS to convert mongo object" )
                  )
                val ttv =
                  textToVar.getOrElse(
                    throw new Exception( "must have textToVar to convert mongo object" )
                  )
                val ttt =
                  textToTag.getOrElse(
                    throw new Exception( "must have textToTag to convert mongo object" )
                  )
                                
                CnxnMongoObjectifier.fromMongoObject( value )( ltns, ttv, ttt ) match {
                  case CnxnCtxtBranch( ns, CnxnCtxtBranch( kNs, k :: Nil ) :: CnxnCtxtBranch( vNs, v :: Nil ) :: Nil ) => {
                    matchMap( key, k ) match {
                      case Some( soln ) => {
                        if ( compareNameSpace( ns, kvNameSpace ) ) {
                          emT.PlaceInstance(
                            k,
                            Left[mTT.Resource,List[Option[mTT.Resource] => Unit @suspendable]](
                              mTT.Ground(
                                asCacheValue(
                                  new CnxnCtxtBranch[String,String,String](
                                    "string",
                                    v :: Nil
                                  )
                                )
                              )
                            ),
                            // BUGBUG -- lgm : why can't the compiler determine
                            // that this cast is not necessary?
                            theEMTypes.PrologSubstitution( soln ).asInstanceOf[emT.Substitution]
                          )
                        }
                        else {
                          if ( compareNameSpace( ns, kvKNameSpace ) ) {
                            val mTT.Continuation( ks ) =
                              asCacheK(
                                new CnxnCtxtBranch[String,String,String](
                                  "string",
                                  v :: Nil
                                )
                              )
                            emT.PlaceInstance(
                              k,
                              Right[mTT.Resource,List[Option[mTT.Resource] => Unit @suspendable]]( 
                                ks
                              ),
                              // BUGBUG -- lgm : why can't the compiler determine
                              // that this cast is not necessary?
                              theEMTypes.PrologSubstitution( soln ).asInstanceOf[emT.Substitution]
                            )
                          }
                          else {
                            throw new Exception( "unexpected namespace : (" + ns + ")" )
                          }
                        }
                      }
                      case None => {
                        BasicLogService.tweet( "Unexpected matchMap failure: " + key + " " + k )
                        throw new Exception( "matchMap failure " + key + " " + k )
                      }
                    }                                           
                  }
                  case _ => {
                    throw new Exception( "unexpected record format : " + value )
                  }
                }                               
              }
              
            }
            override def asCacheK(
              ccl : CnxnCtxtLabel[String,String,String]
            ) : Option[mTT.Continuation] = {
              BasicLogService.tweet(
                "converting to cache continuation stack" + ccl
              )
              ccl match {
                case CnxnCtxtBranch(
                  "string",
                  CnxnCtxtLeaf( Left( rv ) ) :: Nil
                ) => {
                  val unBlob =
                    continuationStorageType match {
                      case "CnxnCtxtLabel" => {
                        // BasicLogService.tweet(
                        //                    "warning: CnxnCtxtLabel method is using XStream"
                        //                  )
                        fromXQSafeJSONBlob( rv )
                      }
                      case "XStream" => {
                        fromXQSafeJSONBlob( rv )
                      }
                      case "Base64" => {
                        val data : Array[Byte] = Base64Coder.decode( rv )
                        val ois : ObjectInputStream =
                          new ObjectInputStream( new ByteArrayInputStream(  data ) )
                        val o : java.lang.Object = ois.readObject();
                        ois.close()
                        o
                      }
                    }
                  
                  unBlob match {
                    case k : mTT.Resource => {
                      Some( k.asInstanceOf[mTT.Continuation] )
                    }
                    case _ => {
                      throw new Exception(
                        (
                          ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                          + "ill-formatted continuation stack blob : " + rv
                          + "\n" 
                          + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                          + "\n"
                          + "unBlob : " + unBlob
                          + "\n"
                          + "unBlob type : " + unBlob
                          + "\n"
                          + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                        )
                      )
                    }
                  }
                }
                case _ => {
                  throw new Exception( "ill-formatted continuation stack leaf: " + ccl )
                }
              }
            }
            
            override def asCacheK(
              ltns : String => String,
              ttv : String => String,
              value : DBObject
            ) : Option[mTT.Continuation] = {
              throw new Exception( "shouldn't be calling this version of asCacheK" )
            }
            override def persistenceManifest : Option[PersistenceManifest] = {
              BasicLogService.tweet(
                (
                  "AgentKVDB : "
                  + "\nthis: " + this
                  + "\n method : persistenceManifest "
                )
              )
              val sid = Some( ( s : String ) => recoverFieldName( s ) )
              val kvdb = this;
              Some(
                new StringMongoDBManifest( dfStoreUnitStr, sid, sid, sid ) {
                  override def valueStorageType : String = {
                    kvdb.valueStorageType
                  }
                  override def continuationStorageType : String = {
                    kvdb.continuationStorageType
                  }
                }
              )
            }
            def dfStoreUnitStr : String = mnkrExchange( name )
          }
        }
        override def ptToPt[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
          here : URI, there : URI
        )(
          implicit configFileNameOpt : Option[String] 
        ) : AgentKVDBNode[ReqBody,RspBody] = {
          val node =
            new AgentKVDBNode[ReqBody,RspBody](
              mkCache( MURI( here ), configFileNameOpt ),
              List( MURI( there ) ),
              None,
              configFileNameOpt
            ) {
              override def mkInnerCache[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse]( 
                here : URI,
                configFileName : Option[String]
              ) : HashAgentKVDB[ReqBody,RspBody] = {
                BasicLogService.tweet(
                  (
                    "AgentKVDBNode : "
                    + "\nthis: " + this
                    + "\n method : mkInnerCache "
                    + "\n here: " + here
                    + "\n configFileName: " + configFileName
                  )
                )
                new HashAgentKVDB[ReqBody, RspBody](
                  MURI( here ),
                  configFileName
                ) with Blobify with AMQPMonikerOps {            
                  override def toXQSafeJSONBlob( x : java.lang.Object ) : String = {
                    new XStream( new JettisonMappedXmlDriver() ).toXML( x )
                  }
                  override def fromXQSafeJSONBlob( blob : String ) : java.lang.Object = {              
                    new XStream( new JettisonMappedXmlDriver() ).fromXML( blob )
                  }      
                  class StringMongoDBManifest(
                    override val storeUnitStr : String,
                    @transient override val labelToNS : Option[String => String],
                    @transient override val textToVar : Option[String => String],
                    @transient override val textToTag : Option[String => String]
                  )
                  extends MongoDBManifest( /* database */ ) {
                    override def valueStorageType : String = {
                      throw new Exception( "valueStorageType not overriden in instantiation" )
                    }
                    override def continuationStorageType : String = {
                      throw new Exception( "continuationStorageType not overriden in instantiation" )
                    }
                    
                    override def storeUnitStr[Src,Label,Trgt]( cnxn : Cnxn[Src,Label,Trgt] ) : String = {     
                      cnxn match {
                        case CCnxn( s, l, t ) => s.toString + l.toString + t.toString
                        case acT.AgentCnxn( s, l, t ) => s.getHost + l.toString + t.getHost
                      }     
                    }   
                    
                    def kvNameSpace : String = "record"
                    def kvKNameSpace : String = "kRecord"
                    
                    def compareNameSpace( ns1 : String, ns2 : String ) : Boolean = {
                      ns1.equals( ns2 )
                    }
                    
                    override def asStoreValue(
                      rsrc : mTT.Resource
                    ) : CnxnCtxtLeaf[String,String,String] with Factual = {
                      BasicLogService.tweet(
                        "In asStoreValue on " + this + " for resource: " + rsrc
                      )
                      val storageDispatch = 
                        rsrc match {
                          case k : mTT.Continuation => {
                            BasicLogService.tweet(
                              "Resource " + rsrc + " is a continuation"
                            )
                            continuationStorageType
                          }
                          case _ => {
                            BasicLogService.tweet(
                              "Resource " + rsrc + " is a value"
                            )
                            valueStorageType
                          }
                        };
                      
                      BasicLogService.tweet(
                        "storageDispatch: " + storageDispatch
                      )
                      
                      val blob =
                        storageDispatch match {
                          case "Base64" => {
                            val baos : ByteArrayOutputStream = new ByteArrayOutputStream()
                            val oos : ObjectOutputStream = new ObjectOutputStream( baos )
                            oos.writeObject( rsrc.asInstanceOf[Serializable] )
                            oos.close()
                            new String( Base64Coder.encode( baos.toByteArray() ) )
                          }
                          case "CnxnCtxtLabel" => {
                            BasicLogService.tweet(
                              "warning: CnxnCtxtLabel method is using XStream"
                            )
                            toXQSafeJSONBlob( rsrc )                              
                          }
                          case "XStream" => {
                            BasicLogService.tweet(
                              "using XStream method"
                            )
                            
                            toXQSafeJSONBlob( rsrc )
                          }
                          case _ => {
                            throw new Exception( "unexpected value storage type" )
                          }
                        }
                      new CnxnCtxtLeaf[String,String,String](
                        Left[String,String]( blob )
                      )
                    }
                    
                    def asCacheValue(
                      ccl : CnxnCtxtLabel[String,String,String]
                    ) : ConcreteHL.HLExpr = {
                      BasicLogService.tweet(
                        "converting to cache value"
                      )
                      ccl match {
                        case CnxnCtxtBranch(
                          "string",
                          CnxnCtxtLeaf( Left( rv ) ) :: Nil
                        ) => {
                          val unBlob =
                            fromXQSafeJSONBlob( rv )
                          
                          unBlob match {
                            case rsrc : mTT.Resource => {
                              getGV( rsrc ).getOrElse( ConcreteHL.Bottom )
                            }
                          }
                        }
                        case _ => {
                          //asPatternString( ccl )
                          throw new Exception( "unexpected value form: " + ccl )
                        }
                      }
                    }
                    
                    override def asResource(
                      key : mTT.GetRequest, // must have the pattern to determine bindings
                      value : DBObject
                    ) : emT.PlaceInstance = {
                      val ltns =
                        labelToNS.getOrElse(
                          throw new Exception( "must have labelToNS to convert mongo object" )
                        )
                      val ttv =
                        textToVar.getOrElse(
                          throw new Exception( "must have textToVar to convert mongo object" )
                        )
                      val ttt =
                        textToTag.getOrElse(
                          throw new Exception( "must have textToTag to convert mongo object" )
                        )
                      //val ttt = ( x : String ) => x
                      
                      //val ptn = asPatternString( key )
                      //println( "ptn : " + ptn )               
                      
                      CnxnMongoObjectifier.fromMongoObject( value )( ltns, ttv, ttt ) match {
                        case CnxnCtxtBranch( ns, CnxnCtxtBranch( kNs, k :: Nil ) :: CnxnCtxtBranch( vNs, v :: Nil ) :: Nil ) => {
                          matchMap( key, k ) match {
                            case Some( soln ) => {
                              if ( compareNameSpace( ns, kvNameSpace ) ) {
                                emT.PlaceInstance(
                                  k,
                                  Left[mTT.Resource,List[Option[mTT.Resource] => Unit @suspendable]](
                                    mTT.Ground(
                                      asCacheValue(
                                        new CnxnCtxtBranch[String,String,String](
                                          "string",
                                          v :: Nil
                                        )
                                      )
                                    )
                                  ),
                                  // BUGBUG -- lgm : why can't the compiler determine
                                  // that this cast is not necessary?
                                  theEMTypes.PrologSubstitution( soln ).asInstanceOf[emT.Substitution]
                                )
                              }
                              else {
                                if ( compareNameSpace( ns, kvKNameSpace ) ) {
                                  val mTT.Continuation( ks ) =
                                    asCacheK(
                                      new CnxnCtxtBranch[String,String,String](
                                        "string",
                                        v :: Nil
                                      )
                                    )
                                  emT.PlaceInstance(
                                    k,
                                    Right[mTT.Resource,List[Option[mTT.Resource] => Unit @suspendable]]( 
                                      ks
                                    ),
                                    // BUGBUG -- lgm : why can't the compiler determine
                                    // that this cast is not necessary?
                                    theEMTypes.PrologSubstitution( soln ).asInstanceOf[emT.Substitution]
                                  )
                                }
                                else {
                                  throw new Exception( "unexpected namespace : (" + ns + ")" )
                                }
                              }
                            }
                            case None => {
                              BasicLogService.tweet( "Unexpected matchMap failure: " + key + " " + k )
                              throw new Exception( "matchMap failure " + key + " " + k )
                            }
                          }                                             
                        }
                        case _ => {
                          throw new Exception( "unexpected record format : " + value )
                        }
                      }
                    }
                    
                  }
                  override def asCacheK(
                    ccl : CnxnCtxtLabel[String,String,String]
                  ) : Option[mTT.Continuation] = {
                    BasicLogService.tweet(
                      "converting to cache continuation stack" + ccl
                    )
                    ccl match {
                      case CnxnCtxtBranch(
                        "string",
                        CnxnCtxtLeaf( Left( rv ) ) :: Nil
                      ) => {
                        val unBlob =
                          continuationStorageType match {
                            case "CnxnCtxtLabel" => {
                              // BasicLogService.tweet(
                              //                      "warning: CnxnCtxtLabel method is using XStream"
                              //                    )
                              fromXQSafeJSONBlob( rv )
                            }
                            case "XStream" => {
                              fromXQSafeJSONBlob( rv )
                            }
                            case "Base64" => {
                              val data : Array[Byte] = Base64Coder.decode( rv )
                              val ois : ObjectInputStream =
                                new ObjectInputStream( new ByteArrayInputStream(  data ) )
                              val o : java.lang.Object = ois.readObject();
                              ois.close()
                              o
                            }
                          }
                        
                        unBlob match {
                          case k : mTT.Resource => {
                            Some( k.asInstanceOf[mTT.Continuation] )
                          }
                          case _ => {
                            throw new Exception(
                              (
                                ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                + "ill-formatted continuation stack blob : " + rv
                                + "\n" 
                                + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                + "\n"
                                + "unBlob : " + unBlob
                                + "\n"
                                + "unBlob type : " + unBlob
                                + "\n"
                                + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                              )
                            )
                          }
                        }
                      }
                      case _ => {
                        throw new Exception( "ill-formatted continuation stack leaf: " + ccl )
                      }
                    }
                  }
                  
                  override def asCacheK(
                    ltns : String => String,
                    ttv : String => String,
                    value : DBObject
                  ) : Option[mTT.Continuation] = {
                    throw new Exception( "shouldn't be calling this version of asCacheK" )
                  }

                  override def persistenceManifest : Option[PersistenceManifest] = {
                    BasicLogService.tweet(
                      (
                        "HashAgentKVDB : "
                        + "\nthis: " + this
                        + "\n method : persistenceManifest "
                      )
                    )
                    val sid = Some( ( s : String ) => recoverFieldName( s ) )
                    val kvdb = this;
                    Some(
                      new StringMongoDBManifest( dfStoreUnitStr, sid, sid, sid ) {
                        override def valueStorageType : String = {
                          kvdb.valueStorageType
                        }
                        override def continuationStorageType : String = {
                          kvdb.continuationStorageType
                        }
                      }
                    )
                  }
                  def dfStoreUnitStr : String = mnkrExchange( name )
                }
              }
            }
          spawn {
            node.dispatchDMsgs()
          }
          node
        }
        override def ptToMany[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
          here : URI, there : List[URI]
        )(
          implicit configFileNameOpt : Option[String]
        ) : AgentKVDBNode[ReqBody,RspBody] = {
          val node =
            new AgentKVDBNode[ReqBody,RspBody](
              mkCache( MURI( here ), configFileNameOpt ),
              there.map( MURI( _ ) ),
              None,
              configFileNameOpt
            ) {
              override def mkInnerCache[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse]( 
                here : URI,
                configFileName : Option[String]
              ) : HashAgentKVDB[ReqBody,RspBody] = {
                BasicLogService.tweet(
                  (
                    "AgentKVDBNode : "
                    + "\nthis: " + this
                    + "\n method : mkInnerCache "
                    + "\n here: " + here
                    + "\n configFileName: " + configFileName
                  )
                )
                new HashAgentKVDB[ReqBody, RspBody](
                  MURI( here ),
                  configFileName
                ) with Blobify with AMQPMonikerOps {            
                  override def toXQSafeJSONBlob( x : java.lang.Object ) : String = {
                    new XStream( new JettisonMappedXmlDriver() ).toXML( x )
                  }
                  override def fromXQSafeJSONBlob( blob : String ) : java.lang.Object = {              
                    new XStream( new JettisonMappedXmlDriver() ).fromXML( blob )
                  }      
                  class StringMongoDBManifest(
                    override val storeUnitStr : String,
                    @transient override val labelToNS : Option[String => String],
                    @transient override val textToVar : Option[String => String],
                    @transient override val textToTag : Option[String => String]
                  )
                  extends MongoDBManifest( /* database */ ) {
                    override def valueStorageType : String = {
                      throw new Exception( "valueStorageType not overriden in instantiation" )
                    }
                    override def continuationStorageType : String = {
                      throw new Exception( "continuationStorageType not overriden in instantiation" )
                    }
                    
                    override def storeUnitStr[Src,Label,Trgt]( cnxn : Cnxn[Src,Label,Trgt] ) : String = {     
                      cnxn match {
                        case CCnxn( s, l, t ) => s.toString + l.toString + t.toString
                        case acT.AgentCnxn( s, l, t ) => s.getHost + l.toString + t.getHost
                      }     
                    }   
                    
                    def kvNameSpace : String = "record"
                    def kvKNameSpace : String = "kRecord"
                    
                    def compareNameSpace( ns1 : String, ns2 : String ) : Boolean = {
                      ns1.equals( ns2 )
                    }
                    
                    override def asStoreValue(
                      rsrc : mTT.Resource
                    ) : CnxnCtxtLeaf[String,String,String] with Factual = {
                      BasicLogService.tweet(
                        "In asStoreValue on " + this + " for resource: " + rsrc
                      )
                      val storageDispatch = 
                        rsrc match {
                          case k : mTT.Continuation => {
                            BasicLogService.tweet(
                              "Resource " + rsrc + " is a continuation"
                            )
                            continuationStorageType
                          }
                          case _ => {
                            BasicLogService.tweet(
                              "Resource " + rsrc + " is a value"
                            )
                            valueStorageType
                          }
                        };
                      
                      BasicLogService.tweet(
                        "storageDispatch: " + storageDispatch
                      )
                      
                      val blob =
                        storageDispatch match {
                          case "Base64" => {
                            val baos : ByteArrayOutputStream = new ByteArrayOutputStream()
                            val oos : ObjectOutputStream = new ObjectOutputStream( baos )
                            oos.writeObject( rsrc.asInstanceOf[Serializable] )
                            oos.close()
                            new String( Base64Coder.encode( baos.toByteArray() ) )
                          }
                          case "CnxnCtxtLabel" => {
                            BasicLogService.tweet(
                              "warning: CnxnCtxtLabel method is using XStream"
                            )
                            toXQSafeJSONBlob( rsrc )                              
                          }
                          case "XStream" => {
                            BasicLogService.tweet(
                              "using XStream method"
                            )
                            
                            toXQSafeJSONBlob( rsrc )
                          }
                          case _ => {
                            throw new Exception( "unexpected value storage type" )
                          }
                        }
                      new CnxnCtxtLeaf[String,String,String](
                        Left[String,String]( blob )
                      )
                    }
                    
                    def asCacheValue(
                      ccl : CnxnCtxtLabel[String,String,String]
                    ) : ConcreteHL.HLExpr = {
                      BasicLogService.tweet(
                        "converting to cache value"
                      )
                      ccl match {
                        case CnxnCtxtBranch(
                          "string",
                          CnxnCtxtLeaf( Left( rv ) ) :: Nil
                        ) => {
                          val unBlob =
                            fromXQSafeJSONBlob( rv )
                          
                          unBlob match {
                            case rsrc : mTT.Resource => {
                              getGV( rsrc ).getOrElse( ConcreteHL.Bottom )
                            }
                          }
                        }
                        case _ => {
                          //asPatternString( ccl )
                          throw new Exception( "unexpected value form: " + ccl )
                        }
                      }
                    }
                    
                    override def asResource(
                      key : mTT.GetRequest, // must have the pattern to determine bindings
                      value : DBObject
                    ) : emT.PlaceInstance = {
                      val ltns =
                        labelToNS.getOrElse(
                          throw new Exception( "must have labelToNS to convert mongo object" )
                        )
                      val ttv =
                        textToVar.getOrElse(
                          throw new Exception( "must have textToVar to convert mongo object" )
                        )
                      val ttt =
                        textToTag.getOrElse(
                          throw new Exception( "must have textToTag to convert mongo object" )
                        )
                      //val ttt = ( x : String ) => x
                      
                      //val ptn = asPatternString( key )
                      //println( "ptn : " + ptn )               
                      
                      CnxnMongoObjectifier.fromMongoObject( value )( ltns, ttv, ttt ) match {
                        case CnxnCtxtBranch( ns, CnxnCtxtBranch( kNs, k :: Nil ) :: CnxnCtxtBranch( vNs, v :: Nil ) :: Nil ) => {
                          matchMap( key, k ) match {
                            case Some( soln ) => {
                              if ( compareNameSpace( ns, kvNameSpace ) ) {
                                emT.PlaceInstance(
                                  k,
                                  Left[mTT.Resource,List[Option[mTT.Resource] => Unit @suspendable]](
                                    mTT.Ground(
                                      asCacheValue(
                                        new CnxnCtxtBranch[String,String,String](
                                          "string",
                                          v :: Nil
                                        )
                                      )
                                    )
                                  ),
                                  // BUGBUG -- lgm : why can't the compiler determine
                                  // that this cast is not necessary?
                                  theEMTypes.PrologSubstitution( soln ).asInstanceOf[emT.Substitution]
                                )
                              }
                              else {
                                if ( compareNameSpace( ns, kvKNameSpace ) ) {
                                  val mTT.Continuation( ks ) =
                                    asCacheK(
                                      new CnxnCtxtBranch[String,String,String](
                                        "string",
                                        v :: Nil
                                      )
                                    )
                                  emT.PlaceInstance(
                                    k,
                                    Right[mTT.Resource,List[Option[mTT.Resource] => Unit @suspendable]]( 
                                      ks
                                    ),
                                    // BUGBUG -- lgm : why can't the compiler determine
                                    // that this cast is not necessary?
                                    theEMTypes.PrologSubstitution( soln ).asInstanceOf[emT.Substitution]
                                  )
                                }
                                else {
                                  throw new Exception( "unexpected namespace : (" + ns + ")" )
                                }
                              }
                            }
                            case None => {
                              BasicLogService.tweet( "Unexpected matchMap failure: " + key + " " + k )
                              throw new UnificationQueryFilter( key, k, value )
                            }
                          }                                             
                        }
                        case _ => {
                          throw new Exception( "unexpected record format : " + value )
                        }
                      }
                    }
                    
                  }
                  override def asCacheK(
                    ccl : CnxnCtxtLabel[String,String,String]
                  ) : Option[mTT.Continuation] = {
                    BasicLogService.tweet(
                      "converting to cache continuation stack" + ccl
                    )
                    ccl match {
                      case CnxnCtxtBranch(
                        "string",
                        CnxnCtxtLeaf( Left( rv ) ) :: Nil
                      ) => {
                        val unBlob =
                          continuationStorageType match {
                            case "CnxnCtxtLabel" => {
                              // BasicLogService.tweet(
                              //                      "warning: CnxnCtxtLabel method is using XStream"
                              //                    )
                              fromXQSafeJSONBlob( rv )
                            }
                            case "XStream" => {
                              fromXQSafeJSONBlob( rv )
                            }
                            case "Base64" => {
                              val data : Array[Byte] = Base64Coder.decode( rv )
                              val ois : ObjectInputStream =
                                new ObjectInputStream( new ByteArrayInputStream(  data ) )
                              val o : java.lang.Object = ois.readObject();
                              ois.close()
                              o
                            }
                          }
                        
                        unBlob match {
                          case k : mTT.Resource => {
                            Some( k.asInstanceOf[mTT.Continuation] )
                          }
                          case _ => {
                            throw new Exception(
                              (
                                ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                + "ill-formatted continuation stack blob : " + rv
                                + "\n" 
                                + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                + "\n"
                                + "unBlob : " + unBlob
                                + "\n"
                                + "unBlob type : " + unBlob
                                + "\n"
                                + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                              )
                            )
                          }
                        }
                      }
                      case _ => {
                        throw new Exception( "ill-formatted continuation stack leaf: " + ccl )
                      }
                    }
                  }
                  
                  override def asCacheK(
                    ltns : String => String,
                    ttv : String => String,
                    value : DBObject
                  ) : Option[mTT.Continuation] = {
                    throw new Exception( "shouldn't be calling this version of asCacheK" )
                  }
                  override def persistenceManifest : Option[PersistenceManifest] = {
                    BasicLogService.tweet(
                      (
                        "HashAgentKVDB : "
                        + "\nthis: " + this
                        + "\n method : persistenceManifest "
                      )
                    )
                    val sid = Some( ( s : String ) => recoverFieldName( s ) )
                    val kvdb = this;
                    Some(
                      new StringMongoDBManifest( dfStoreUnitStr, sid, sid, sid ) {
                        override def valueStorageType : String = {
                          kvdb.valueStorageType
                        }
                        override def continuationStorageType : String = {
                          kvdb.continuationStorageType
                        }
                      }
                    )
                  }
                  def dfStoreUnitStr : String = mnkrExchange( name )
                }
              }
            }
          spawn {
            BasicLogService.tweet( "initiating dispatch on " + node )
            node.dispatchDMsgs()
          }
          node
        }
        def loopBack[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
          here : URI
        )(
          implicit configFileNameOpt : Option[String]
        ) : AgentKVDBNode[ReqBody,RspBody] = {
          val exchange = uriExchange( here )
          val hereNow =
            new URI(
              here.getScheme,
              here.getUserInfo,
              here.getHost,
              here.getPort,
              "/" + exchange + "Local",
              here.getQuery,
              here.getFragment
            )
          val thereNow =
            new URI(
              here.getScheme,
              here.getUserInfo,
              here.getHost,
              here.getPort,
              "/" + exchange + "Remote",
              here.getQuery,
              here.getFragment
            )       
          
          val node =
            new AgentKVDBNode[ReqBody, RspBody](
              mkCache( MURI( hereNow ), configFileNameOpt ),
              List( MURI( thereNow ) ),
              None,
              configFileNameOpt
            ) {
              override def mkInnerCache[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse]( 
                here : URI,
                configFileName : Option[String]
              ) : HashAgentKVDB[ReqBody,RspBody] = {
                BasicLogService.tweet(
                  (
                    "AgentKVDBNode : "
                    + "\nthis: " + this
                    + "\n method : mkInnerCache "
                    + "\n here: " + here
                    + "\n configFileName: " + configFileName
                  )
                )
                new HashAgentKVDB[ReqBody, RspBody](
                  MURI( here ),
                  configFileName
                ) with Blobify with AMQPMonikerOps {            
                  override def toXQSafeJSONBlob( x : java.lang.Object ) : String = {
                    new XStream( new JettisonMappedXmlDriver() ).toXML( x )
                  }
                  override def fromXQSafeJSONBlob( blob : String ) : java.lang.Object = {              
                    new XStream( new JettisonMappedXmlDriver() ).fromXML( blob )
                  }      
                  class StringMongoDBManifest(
                    override val storeUnitStr : String,
                    @transient override val labelToNS : Option[String => String],
                    @transient override val textToVar : Option[String => String],
                    @transient override val textToTag : Option[String => String]
                  )
                  extends MongoDBManifest( /* database */ ) {
                    override def valueStorageType : String = {
                      throw new Exception( "valueStorageType not overriden in instantiation" )
                    }
                    override def continuationStorageType : String = {
                      throw new Exception( "continuationStorageType not overriden in instantiation" )
                    }
                    
                    override def storeUnitStr[Src,Label,Trgt]( cnxn : Cnxn[Src,Label,Trgt] ) : String = {     
                      cnxn match {
                        case CCnxn( s, l, t ) => s.toString + l.toString + t.toString
                        case acT.AgentCnxn( s, l, t ) => s.getHost + l.toString + t.getHost
                      }     
                    }   
                    
                    def kvNameSpace : String = "record"
                    def kvKNameSpace : String = "kRecord"
                    
                    def compareNameSpace( ns1 : String, ns2 : String ) : Boolean = {
                      ns1.equals( ns2 )
                    }
                    
                    override def asStoreValue(
                      rsrc : mTT.Resource
                    ) : CnxnCtxtLeaf[String,String,String] with Factual = {
                      BasicLogService.tweet(
                        "In asStoreValue on " + this + " for resource: " + rsrc
                      )
                      val storageDispatch = 
                        rsrc match {
                          case k : mTT.Continuation => {
                            BasicLogService.tweet(
                              "Resource " + rsrc + " is a continuation"
                            )
                            continuationStorageType
                          }
                          case _ => {
                            BasicLogService.tweet(
                              "Resource " + rsrc + " is a value"
                            )
                            valueStorageType
                          }
                        };
                      
                      BasicLogService.tweet(
                        "storageDispatch: " + storageDispatch
                      )
                      
                      val blob =
                        storageDispatch match {
                          case "Base64" => {
                            val baos : ByteArrayOutputStream = new ByteArrayOutputStream()
                            val oos : ObjectOutputStream = new ObjectOutputStream( baos )
                            oos.writeObject( rsrc.asInstanceOf[Serializable] )
                            oos.close()
                            new String( Base64Coder.encode( baos.toByteArray() ) )
                          }
                          case "CnxnCtxtLabel" => {
                            BasicLogService.tweet(
                              "warning: CnxnCtxtLabel method is using XStream"
                            )
                            toXQSafeJSONBlob( rsrc )                              
                          }
                          case "XStream" => {
                            BasicLogService.tweet(
                              "using XStream method"
                            )
                            
                            toXQSafeJSONBlob( rsrc )
                          }
                          case _ => {
                            throw new Exception( "unexpected value storage type" )
                          }
                        }
                      new CnxnCtxtLeaf[String,String,String](
                        Left[String,String]( blob )
                      )
                    }
                    
                    def asCacheValue(
                      ccl : CnxnCtxtLabel[String,String,String]
                    ) : ConcreteHL.HLExpr = {
                      BasicLogService.tweet(
                        "converting to cache value"
                      )
                      ccl match {
                        case CnxnCtxtBranch(
                          "string",
                          CnxnCtxtLeaf( Left( rv ) ) :: Nil
                        ) => {
                          val unBlob =
                            fromXQSafeJSONBlob( rv )
                          
                          unBlob match {
                            case rsrc : mTT.Resource => {
                              getGV( rsrc ).getOrElse( ConcreteHL.Bottom )
                            }
                          }
                        }
                        case _ => {
                          //asPatternString( ccl )
                          throw new Exception( "unexpected value form: " + ccl )
                        }
                      }
                    }
                    
                    override def asResource(
                      key : mTT.GetRequest, // must have the pattern to determine bindings
                      value : DBObject
                    ) : emT.PlaceInstance = {
                      val ltns =
                        labelToNS.getOrElse(
                          throw new Exception( "must have labelToNS to convert mongo object" )
                        )
                      val ttv =
                        textToVar.getOrElse(
                          throw new Exception( "must have textToVar to convert mongo object" )
                        )
                      val ttt =
                        textToTag.getOrElse(
                          throw new Exception( "must have textToTag to convert mongo object" )
                        )
                                            
                      CnxnMongoObjectifier.fromMongoObject( value )( ltns, ttv, ttt ) match {
                        case CnxnCtxtBranch( ns, CnxnCtxtBranch( kNs, k :: Nil ) :: CnxnCtxtBranch( vNs, v :: Nil ) :: Nil ) => {
                          matchMap( key, k ) match {
                            case Some( soln ) => {
                              if ( compareNameSpace( ns, kvNameSpace ) ) {
                                emT.PlaceInstance(
                                  k,
                                  Left[mTT.Resource,List[Option[mTT.Resource] => Unit @suspendable]](
                                    mTT.Ground(
                                      asCacheValue(
                                        new CnxnCtxtBranch[String,String,String](
                                          "string",
                                          v :: Nil
                                        )
                                      )
                                    )
                                  ),
                                  // BUGBUG -- lgm : why can't the compiler determine
                                  // that this cast is not necessary?
                                  theEMTypes.PrologSubstitution( soln ).asInstanceOf[emT.Substitution]
                                )
                              }
                              else {
                                if ( compareNameSpace( ns, kvKNameSpace ) ) {
                                  val mTT.Continuation( ks ) =
                                    asCacheK(
                                      new CnxnCtxtBranch[String,String,String](
                                        "string",
                                        v :: Nil
                                      )
                                    )
                                  emT.PlaceInstance(
                                    k,
                                    Right[mTT.Resource,List[Option[mTT.Resource] => Unit @suspendable]]( 
                                      ks
                                    ),
                                    // BUGBUG -- lgm : why can't the compiler determine
                                    // that this cast is not necessary?
                                    theEMTypes.PrologSubstitution( soln ).asInstanceOf[emT.Substitution]
                                  )
                                }
                                else {
                                  throw new Exception( "unexpected namespace : (" + ns + ")" )
                                }
                              }
                            }
                            case None => {
                              BasicLogService.tweet( "Unexpected matchMap failure: " + key + " " + k )
                              throw new Exception( "matchMap failure " + key + " " + k )
                            }
                          }                                             
                        }
                        case _ => {
                          throw new Exception( "unexpected record format : " + value )
                        }
                      }
                    }
                    
                  }
                  override def asCacheK(
                    ccl : CnxnCtxtLabel[String,String,String]
                  ) : Option[mTT.Continuation] = {
                    BasicLogService.tweet(
                      "converting to cache continuation stack" + ccl
                    )
                    ccl match {
                      case CnxnCtxtBranch(
                        "string",
                        CnxnCtxtLeaf( Left( rv ) ) :: Nil
                      ) => {
                        val unBlob =
                          continuationStorageType match {
                            case "CnxnCtxtLabel" => {
                              // BasicLogService.tweet(
                              //                      "warning: CnxnCtxtLabel method is using XStream"
                              //                    )
                              fromXQSafeJSONBlob( rv )
                            }
                            case "XStream" => {
                              fromXQSafeJSONBlob( rv )
                            }
                            case "Base64" => {
                              val data : Array[Byte] = Base64Coder.decode( rv )
                              val ois : ObjectInputStream =
                                new ObjectInputStream( new ByteArrayInputStream(  data ) )
                              val o : java.lang.Object = ois.readObject();
                              ois.close()
                              o
                            }
                          }
                        
                        unBlob match {
                          case k : mTT.Resource => {
                            Some( k.asInstanceOf[mTT.Continuation] )
                          }
                          case _ => {
                            throw new Exception(
                              (
                                ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                + "ill-formatted continuation stack blob : " + rv
                                + "\n" 
                                + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                + "\n"
                                + "unBlob : " + unBlob
                                + "\n"
                                + "unBlob type : " + unBlob
                                + "\n"
                                + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                              )
                            )
                          }
                        }
                      }
                      case _ => {
                        throw new Exception( "ill-formatted continuation stack leaf: " + ccl )
                      }
                    }
                  }
                  
                  override def asCacheK(
                    ltns : String => String,
                    ttv : String => String,
                    value : DBObject
                  ) : Option[mTT.Continuation] = {
                    throw new Exception( "shouldn't be calling this version of asCacheK" )
                  }
                  override def persistenceManifest : Option[PersistenceManifest] = {
                    BasicLogService.tweet(
                      (
                        "HashAgentKVDB : "
                        + "\nthis: " + this
                        + "\n method : persistenceManifest "
                      )
                    )
                    val sid = Some( ( s : String ) => recoverFieldName( s ) )
                    val kvdb = this;
                    Some(
                      new StringMongoDBManifest( dfStoreUnitStr, sid, sid, sid ) {
                        override def valueStorageType : String = {
                          kvdb.valueStorageType
                        }
                        override def continuationStorageType : String = {
                          kvdb.continuationStorageType
                        }
                      }
                    )
                  }
                  def dfStoreUnitStr : String = mnkrExchange( name )
                }
              }
            }
          spawn {
            println( "initiating dispatch on " + node )
            node.dispatchDMsgs()
          }
          node
        }
      }
    }

  }

  trait DSLEvaluatorConfiguration {
    self : EvalConfig =>
      def dslEvaluatorHostName() : String = {
        try {
          evalConfig().getString( "DSLEvaluatorHost" )
        }
        catch {
          case e : Throwable => "localhost" 
        }
      }
    def dslEvaluatorHostPort() : Int = {
      try {
        evalConfig().getInt( "DSLEvaluatorPort" )
      }
      catch {
        case e : Throwable => 5672
      }
    }
    def dslEvaluatorHostData() : String = {
        try {
          evalConfig().getString( "DSLEvaluatorHostData" )
        }
        catch {
          case e : Throwable => "/dieselProtocol" 
        }
      }
    def dslEvaluatorPreferredSupplierHostName() : String = {
        try {
          evalConfig().getString( "DSLEvaluatorPreferredSupplierHost" )
        }
        catch {
          case e : Throwable => "localhost" 
        }
      }
    def dslEvaluatorPreferredSupplierPort() : Int = {
      try {
        evalConfig().getInt( "DSLEvaluatorPreferredSupplierPort" )
      }
      catch {
        case e : Throwable => 5672
      }
    }
    def dslEvaluatorNetwork() : List[String] = {
      import scala.collection.JavaConverters._
      try {
        val rslt : java.util.List[String] =
          evalConfig().getStringList( "DSLEvaluatorNetwork" )
        rslt.asScala.toList
      }
      catch {
        case e : Throwable => List[String]( )
      }
    }
  }

  object DieselConfigurationDefaults extends Serializable {
    val localHost : String = "localhost"
    val localPort : Int = 5672
    val remoteHost : String = "localhost"
    val remotePort : Int = 5672
    val dataLocation : String = "/cnxnTestProtocol"    
  }

  trait DieselManufactureConfiguration extends ConfigurationTrampoline {
    def localHost : String =
      configurationFromFile.get( "localHost" ).getOrElse( bail() )
    def localPort : Int =
      configurationFromFile.get( "localPort" ).getOrElse( bail() ).toInt
    def remoteHost : String =
      configurationFromFile.get( "remoteHost" ).getOrElse( bail() )
    def remotePort : Int =
      configurationFromFile.get( "remotePort" ).getOrElse( bail() ).toInt    
    def dataLocation : String = 
      configurationFromFile.get( "dataLocation" ).getOrElse( bail() )
  }

  object DieselEngineCtor extends EvalConfig
  with DSLCommLinkConfiguration
  with DSLEvaluatorConfiguration
  with Serializable {
    import DieselEngineScope._
    import Being._
    import AgentKVDBNodeFactory._

    import CnxnConversionStringScope._

    import com.protegra_ati.agentservices.store.extensions.StringExtensions._
    import com.protegra_ati.agentservices.protocols.msgs.ProtocolMessage

    type LinkEvalRequestChannel = DSLCommLinkCtor.StdEvaluationRequestChannel
    type EvalChannel[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse] = Being.AgentKVDBNode[ReqBody,RspBody]
    type StdEvalChannel = EvalChannel[PersistedKVDBNodeRequest,PersistedKVDBNodeResponse]

    type ChannelBundle = ( LinkEvalRequestChannel, LinkEvalRequestChannel, StdEvalChannel )

    def setup[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
      dataLocation : String = dslEvaluatorHostData,
      localHost : String = dslEvaluatorHostName,
      localPort : Int = dslEvaluatorHostPort,
      remoteHost : String = dslEvaluatorPreferredSupplierHostName,
      remotePort : Int = dslEvaluatorPreferredSupplierPort
    )(
      implicit returnTwist : Boolean
    ) : Either[EvalChannel[ReqBody,RspBody],(EvalChannel[ReqBody, RspBody],EvalChannel[ReqBody, RspBody])] = {
      val ( localExchange, remoteExchange ) = 
        if ( localHost.equals( remoteHost ) && ( localPort == remotePort ) ) {
          ( dataLocation, dataLocation + "Remote" )       
        }
        else {
          ( dataLocation, dataLocation )          
        }

      if ( returnTwist ) {
        Right[EvalChannel[ReqBody,RspBody],(EvalChannel[ReqBody, RspBody],EvalChannel[ReqBody, RspBody])](
          (
            ptToPt[ReqBody, RspBody](
              new URI( "agent", null, localHost, localPort, localExchange, null, null ),
              new URI( "agent", null, remoteHost, remotePort, remoteExchange, null, null )
            ),
            ptToPt[ReqBody, RspBody](         
              new URI( "agent", null, remoteHost, remotePort, remoteExchange, null, null ),
              new URI( "agent", null, localHost, localPort, localExchange, null, null )
            )
          )
        )
      }
      else {
        Left[EvalChannel[ReqBody, RspBody],(EvalChannel[ReqBody, RspBody],EvalChannel[ReqBody, RspBody])](
          ptToPt(
            new URI( "agent", null, localHost, localPort, localExchange, null, null ),
            new URI( "agent", null, remoteHost, remotePort, remoteExchange, null, null )
          )
        )
      }
    }    

    def setup[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
      localHost : String, localPort : Int,
      remoteHost : String, remotePort : Int
    )(
      implicit returnTwist : Boolean
    ) : Either[EvalChannel[ReqBody,RspBody],(EvalChannel[ReqBody, RspBody],EvalChannel[ReqBody, RspBody])] = {
      setup( "/dieselProtocol", localHost, localPort, remoteHost, remotePort )
    }

    def setupDSLEvaluatorNode[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
      dataLocation : String = dslEvaluatorHostData,
      localHost : String = dslEvaluatorHostName,
      localPort : Int = dslEvaluatorHostPort
    ) : EvalChannel[ReqBody, RspBody] = {      
      ptToMany(
        new URI( "agent", null, localHost, localPort, dataLocation, null, null ),
        List[URI]( )
      )
    }

    def agent[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse]( 
      dataLocation : String
    ) : EvalChannel[ReqBody,RspBody] = {
      val Right( ( client, server ) ) = 
        setup[ReqBody,RspBody](
          dataLocation, "localhost", 5672, "localhost", 5672
        )( true )
      client
    }    

    def dslEvaluatorAgent[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
    ): EvalChannel[ReqBody,RspBody] = {
      setupDSLEvaluatorNode[ReqBody,RspBody]()
    }

    class DieselEngine(
      override val configFileName : Option[String],
      val cnxnGlobal : acT.AgentCnxn = new acT.AgentCnxn("Global".toURI, "", "Global".toURI),
      val version : String = "0.0.1"
    ) extends DieselManufactureConfiguration with Serializable {        
      override def configurationDefaults : ConfigurationDefaults = {
        DieselConfigurationDefaults.asInstanceOf[ConfigurationDefaults]
      }      
      
      def fileNameToCnxn( fileName : String ) : acT.AgentCnxn = {
        val fileNameRoot = fileName.split( '/' ).last
        new acT.AgentCnxn( fileNameRoot.toURI, "", fileNameRoot.toURI )
      } 

      // BUGBUG : lgm -- this code is not in sync with the weak map
      // case -- please introduce abstraction!!!

      def evaluateExpression[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
        node : EvalChannel[ReqBody,RspBody]
      )( expr : ConcreteHL.HLExpr )(
        handler : Option[mTT.Resource] => Unit
      ): Unit = {
        BasicLogService.tweet(
          "entering method: evaluateExpression"
          + "\nthis: " + this
          + "\nnode: " + node
          + "\nexpr: " + expr
          + "\nhandler: " + handler
        )
        expr match {
          case ConcreteHL.Bottom => {
            //throw new Exception( "divergence" )
            //println( "warning: divergent expression" )
            BasicLogService.tweet( "warning: divergent expression" )
            handler( None )
          }
          case ConcreteHL.FeedExpr( filter, cnxns ) => {
            BasicLogService.tweet(
              "method: evaluateExpression"
              + "\nin ConcreteHL.FeedExpr case "
              + "\nthis: " + this
              + "\nnode: " + node
              + "\nexpr: " + expr
              + "\nhandler: " + handler
              + "\n-----------------------------------------"
              + "\nfilter: " + filter
              + "\ncnxns: " + cnxns
            )
            
            for( cnxn <- cnxns ) {
              val agntCnxn : acT.AgentCnxn =
                new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
              reset {
                
                BasicLogService.tweet(
                  "method: evaluateExpression"
                  + "\n calling node.fetch "
                  + "\nthis: " + this
                  + "\nnode: " + node
                  + "\nexpr: " + expr
                  + "\nhandler: " + handler
                  + "\n-----------------------------------------"
                  + "\nagntCnxn: " + agntCnxn
                  + "\nfilter: " + filter
                )

                for( e <- node.fetch( agntCnxn )( filter ) ) {

                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n returned from node.fetch "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                    + "\ne: " + e
                  )

                  handler( e )
                }
              }
            }
          }
          case ConcreteHL.ScoreExpr( filter, cnxns, staff ) => {
            
            BasicLogService.tweet(
              "method: evaluateExpression"
              + "\nin ConcreteHL.ScoreExpr case "
              + "\nthis: " + this
              + "\nnode: " + node
              + "\nexpr: " + expr
              + "\nhandler: " + handler
              + "\n-----------------------------------------"
              + "\nfilter: " + filter
              + "\ncnxns: " + cnxns
              + "\ncnxns: " + staff
            )

            for( cnxn <- cnxns ) {
              val agntCnxn : acT.AgentCnxn =
                new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
              reset {
                BasicLogService.tweet(
                  "method: evaluateExpression"
                  + "\n calling node.subscribe "
                  + "\nthis: " + this
                  + "\nnode: " + node
                  + "\nexpr: " + expr
                  + "\nhandler: " + handler
                  + "\n-----------------------------------------"
                  + "\nagntCnxn: " + agntCnxn
                  + "\nfilter: " + filter
                )

                for( e <- node.fetch( agntCnxn )( filter ) ) {
                  
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n returned from node.fetch "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                    + "\ne: " + e
                  )

                  handler( e )
                }
              }
            }
          }
          case ConcreteHL.CancelExpr( filter, cnxns ) => {
            
            BasicLogService.tweet(
              "method: evaluateExpression"
              + "\nin ConcreteHL.CancelExpr case "
              + "\nthis: " + this
              + "\nnode: " + node
              + "\nexpr: " + expr
              + "\nhandler: " + handler
              + "\n-----------------------------------------"
              + "\nfilter: " + filter
              + "\ncnxns: " + cnxns
            )

            for( cnxn <- cnxns ) {
              val agntCnxn : acT.AgentCnxn =
                new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )

              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\n calling node.pullCnxnKRecords "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nagntCnxn: " + agntCnxn
                + "\nfilter: " + filter
              )

              for( e <- node.pullCnxnKRecords( agntCnxn )( filter ) ) {
                
                BasicLogService.tweet(
                  "method: evaluateExpression"
                  + "\n returned from node.pullCnxnKRecords "
                  + "\nthis: " + this
                  + "\nnode: " + node
                  + "\nexpr: " + expr
                  + "\nhandler: " + handler
                  + "\n-----------------------------------------"
                  + "\nagntCnxn: " + agntCnxn
                  + "\nfilter: " + filter
                  + "\ne: " + e
                )
                val optRsrc: Option[mTT.Resource] = e.stuff match {
                  case Left(r) => Some(r)
                  case _ => None
                }

                handler( optRsrc )
              }
            }
          }
          case ConcreteHL.InsertContent( filter, cnxns, value : String ) => {
            
            BasicLogService.tweet(
              "method: evaluateExpression"
              + "\nin ConcreteHL.InsertContent case "
              + "\nthis: " + this
              + "\nnode: " + node
              + "\nexpr: " + expr
              + "\nhandler: " + handler
              + "\n-----------------------------------------"
              + "\nfilter: " + filter
              + "\ncnxns: " + cnxns
              + "\nvalue: " + value
            )
              
            for( cnxn <- cnxns ) {
              val agntCnxn : acT.AgentCnxn =
                new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
              reset {
                
                BasicLogService.tweet(
                  "method: evaluateExpression"                  
                  + "\n calling node.publish "
                  + "\nthis: " + this
                  + "\nnode: " + node
                  + "\nexpr: " + expr
                  + "\nhandler: " + handler
                  + "\n-----------------------------------------"
                  + "\nagntCnxn: " + agntCnxn
                  + "\nfilter: " + filter
                  + "\nvalue: " + value
                )

                node.publish( agntCnxn )( filter, mTT.Ground( ConcreteHL.PostedExpr( value ) ) )
              }

              handler( Some( mTT.Ground( ConcreteHL.Bottom ) ) )
            }
          }
          case ConcreteHL.InsertContentV( filter, cnxns, value : AnyRef ) => {
            
            BasicLogService.tweet(
              "method: evaluateExpression"
              + "\nin ConcreteHL.InsertContentV case "
              + "\nthis: " + this
              + "\nnode: " + node
              + "\nexpr: " + expr
              + "\nhandler: " + handler
              + "\n-----------------------------------------"
              + "\nfilter: " + filter
              + "\ncnxns: " + cnxns
              + "\nvalue: " + value
            )
              
            for( cnxn <- cnxns ) {
              val agntCnxn : acT.AgentCnxn =
                new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
              reset {
                
                BasicLogService.tweet(
                  "method: evaluateExpression"                  
                  + "\n calling node.publish "
                  + "\nthis: " + this
                  + "\nnode: " + node
                  + "\nexpr: " + expr
                  + "\nhandler: " + handler
                  + "\n-----------------------------------------"
                  + "\nagntCnxn: " + agntCnxn
                  + "\nfilter: " + filter
                  + "\nvalue: " + value
                )

                node.publish( agntCnxn )( filter, mTT.Ground( ConcreteHL.PostedExpr( value ) ) )
              }

              handler( Some( mTT.Ground( ConcreteHL.Bottom ) ) )
            }
          }
        }
      }

      def evaluateExpression[ReqBody <: PersistedKVDBNodeRequest, RspBody <: PersistedKVDBNodeResponse](
        node : String
      )( expr : ConcreteHL.HLExpr )(
        handler : (Option[mTT.Resource], Option[CnxnCtxtLabel[String,String,String]], Option[acT.AgentCnxn]) => Unit
      ): Unit = {
        BasicLogService.tweet(
          "entering method: evaluateExpression"
          + "\nthis: " + this
          + "\nnode: " + node
          + "\nexpr: " + expr
          + "\nhandler: " + handler
          + "\n-----------------------------------------"
          + "\n n: " + EvalNodeMapper.get( node )
        )
        for ( n <- EvalNodeMapper.get( node ) ) {
          expr match {
            case ConcreteHL.Bottom => {
              //throw new Exception( "divergence" )
              //println( "warning: divergent expression" )
              BasicLogService.tweet( "warning: divergent expression" )
              handler( None, None, None )
            }
            case ConcreteHL.ReadExpr( filter, cnxns ) => {
              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.ReadExpr case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
              )
              
              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {
                  
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.read "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                  )
                  
                  for( e <- n.read( agntCnxn )( filter ) ) {
                    
                    BasicLogService.tweet(
                      "method: evaluateExpression"
                      + "\n returned from node.read "
                      + "\nthis: " + this
                      + "\nnode: " + node
                      + "\nexpr: " + expr
                      + "\nhandler: " + handler
                      + "\n-----------------------------------------"
                      + "\nagntCnxn: " + agntCnxn
                      + "\nfilter: " + filter
                      + "\ne: " + e
                    )
                    
                    handler( e, Some(filter), Some(agntCnxn) )
                  }
                }
              }
            }
            case ConcreteHL.FetchExpr( filter, cnxns ) => {
              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.FetchExpr case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
              )
              
              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {
                  
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.fetch "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                  )
                  
                  for( e <- n.fetch( agntCnxn )( filter ) ) {
                    
                    BasicLogService.tweet(
                      "method: evaluateExpression"
                      + "\n returned from node.fetch "
                      + "\nthis: " + this
                      + "\nnode: " + node
                      + "\nexpr: " + expr
                      + "\nhandler: " + handler
                      + "\n-----------------------------------------"
                      + "\nagntCnxn: " + agntCnxn
                      + "\nfilter: " + filter
                      + "\ne: " + e
                    )
                    
                    handler( e, Some(filter), Some(agntCnxn) )
                  }
                }
              }
            }
            case ConcreteHL.FeedExpr( filter, cnxns ) => {
              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.FeedExpr case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
              )
              
              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {
                  
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.subscribe "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                  )
                  
                  for( e <- n.subscribe( agntCnxn )( filter ) ) {
                    
                    BasicLogService.tweet(
                      "method: evaluateExpression"
                      + "\n returned from node.subscribe "
                      + "\nthis: " + this
                      + "\nnode: " + node
                      + "\nexpr: " + expr
                      + "\nhandler: " + handler
                      + "\n-----------------------------------------"
                      + "\nagntCnxn: " + agntCnxn
                      + "\nfilter: " + filter
                      + "\ne: " + e
                    )
                    
                    handler( e, Some(filter), Some(agntCnxn) )
                  }
                }
              }
            }

            case ConcreteHL.GetExpr( filter, cnxns ) => {
              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.GetExpr case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
              )
              
              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {
                  
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.get "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                  )
                  
                  for( e <- n.get( agntCnxn )( filter ) ) {
                    
                    BasicLogService.tweet(
                      "method: evaluateExpression"
                      + "\n returned from node.get "
                      + "\nthis: " + this
                      + "\nnode: " + node
                      + "\nexpr: " + expr
                      + "\nhandler: " + handler
                      + "\n-----------------------------------------"
                      + "\nagntCnxn: " + agntCnxn
                      + "\nfilter: " + filter
                      + "\ne: " + e
                    )
                    
                    handler( e, Some(filter), Some(agntCnxn) )
                  }
                }
              }
            }
            case ConcreteHL.ScoreExpr( filter, cnxns, staff ) => {
              
              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.ScoreExpr case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
                + "\ncnxns: " + staff
              )
              
              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.subscribe "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                  )
                  
                  for( e <- n.subscribe( agntCnxn )( filter ) ) {
                    
                    BasicLogService.tweet(
                      "method: evaluateExpression"
                      + "\n returned from node.subscribe "
                      + "\nthis: " + this
                      + "\nnode: " + node
                      + "\nexpr: " + expr
                      + "\nhandler: " + handler
                      + "\n-----------------------------------------"
                      + "\nagntCnxn: " + agntCnxn
                      + "\nfilter: " + filter
                      + "\ne: " + e
                    )
                    
                    handler( e, Some(filter), Some(agntCnxn) )
                  }
                }
              }
            }
            case ConcreteHL.CancelExpr( filter, cnxns ) => {

              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.CancelExpr case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
              )

              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                BasicLogService.tweet(
                  "method: evaluateExpression"
                  + "\n calling node.pullCnxnKRecords "
                  + "\nthis: " + this
                  + "\nnode: " + node
                  + "\nexpr: " + expr
                  + "\nhandler: " + handler
                  + "\n-----------------------------------------"
                  + "\nagntCnxn: " + agntCnxn
                  + "\nfilter: " + filter
                )

                for( e <- n.pullCnxnKRecords( agntCnxn )( filter ) ) {

                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n returned from node.pullCnxnKRecords "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                    + "\ne: " + e
                  )
                  val optRsrc: Option[mTT.Resource] = e.stuff match {
                    case Left(r) => Some(r)
                    case _ => None
                  }
                  handler( optRsrc, None, None )
                }
              }
            }
            case ConcreteHL.InsertContent( filter, cnxns, value : String ) => {
              
              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.InsertContent(String) case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
                + "\nvalue: " + value 
              )
              
              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {
                  
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.publish "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                    + "\nvalue: " + value
                  )
                  
                  try {
                    n.publish( agntCnxn )( filter, mTT.Ground( ConcreteHL.PostedExpr( value ) ) )
                  } 
                  catch {
                    case e : Exception => {
                      BasicLogService.tweet(
                        "method: evaluateExpression"
                        + "\n ---> node.publish caused an exception <--- "
                        + "\nthis: " + this
                        + "\nnode: " + node
                        + "\nexpr: " + expr
                        + "\nhandler: " + handler
                        + "\n-----------------------------------------"
                        + "\nagntCnxn: " + agntCnxn
                        + "\nfilter: " + filter
                        + "\nvalue: " + value
                      )
                      BasicLogService.tweetTrace( e )
                    }
                  }
                }

                BasicLogService.tweet(
                  "method: evaluateExpression"
                  + "\n completed node.publish "
                  + "\nthis: " + this
                  + "\nnode: " + node
                  + "\nexpr: " + expr
                  + "\nhandler: " + handler
                  + "\n-----------------------------------------"
                  + "\nagntCnxn: " + agntCnxn
                  + "\nfilter: " + filter
                  + "\nvalue: " + value
                )
                
                handler( Some( mTT.Ground( ConcreteHL.Bottom ) ), Some(filter), Some(agntCnxn) )
              }
            }
            case ConcreteHL.InsertContent( filter, cnxns, value : ProtocolMessage ) => {

              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.InsertContent(ProtocolMessage) case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
                + "\nvalue: " + value
              )

              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {

                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.publish "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                    + "\nvalue: " + value
                  )

                  n.publish( agntCnxn )( filter, mTT.Ground( ConcreteHL.PostedExpr( value ) ) )
                }

                handler( Some( mTT.Ground( ConcreteHL.Bottom ) ), Some( filter ), Some( agntCnxn ) )
              }
            }
            case ConcreteHL.InsertContentV( filter, cnxns, value : AnyRef ) => {

              BasicLogService.tweet(
                "method: evaluateExpression"
                  + "\nin ConcreteHL.InsertContentV case "
                  + "\nthis: " + this
                  + "\nnode: " + node
                  + "\nexpr: " + expr
                  + "\nhandler: " + handler
                  + "\n-----------------------------------------"
                  + "\nfilter: " + filter
                  + "\ncnxns: " + cnxns
                  + "\nvalue: " + value
              )

              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {

                  BasicLogService.tweet(
                    "method: evaluateExpression"
                      + "\n calling node.publish "
                      + "\nthis: " + this
                      + "\nnode: " + node
                      + "\nexpr: " + expr
                      + "\nhandler: " + handler
                      + "\n-----------------------------------------"
                      + "\nagntCnxn: " + agntCnxn
                      + "\nfilter: " + filter
                      + "\nvalue: " + value
                  )

                  n.publish( agntCnxn )( filter, mTT.Ground( ConcreteHL.PostedExpr( value ) ) )
                }

                handler( Some( mTT.Ground( ConcreteHL.Bottom ) ), Some( filter ), Some( agntCnxn ) )
              }
            }
            case ConcreteHL.PutContent( filter, cnxns, value : String ) => {
              
              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.PutContent(String) case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
                + "\nvalue: " + value 
              )
              
              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {
                  
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.put "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                    + "\nvalue: " + value
                  )
                  
                  n.put( agntCnxn )( filter, mTT.Ground( ConcreteHL.PostedExpr( value ) ) )
                }
                
                handler( Some( mTT.Ground( ConcreteHL.Bottom ) ), Some( filter ), Some( agntCnxn ) )
              }
            }
            case ConcreteHL.PutContent( filter, cnxns, value : ProtocolMessage ) => {
              
              BasicLogService.tweet(
                "method: evaluateExpression"
                + "\nin ConcreteHL.PutContent(ProtocolMessage) case "
                + "\nthis: " + this
                + "\nnode: " + node
                + "\nexpr: " + expr
                + "\nhandler: " + handler
                + "\n-----------------------------------------"
                + "\nfilter: " + filter
                + "\ncnxns: " + cnxns
                + "\nvalue: " + value 
              )
              
              for( cnxn <- cnxns ) {
                val agntCnxn : acT.AgentCnxn =
                  new acT.AgentCnxn( cnxn.src, cnxn.label.toString, cnxn.trgt )
                reset {
                  
                  BasicLogService.tweet(
                    "method: evaluateExpression"
                    + "\n calling node.put "
                    + "\nthis: " + this
                    + "\nnode: " + node
                    + "\nexpr: " + expr
                    + "\nhandler: " + handler
                    + "\n-----------------------------------------"
                    + "\nagntCnxn: " + agntCnxn
                    + "\nfilter: " + filter
                    + "\nvalue: " + value
                  )
                  
                  n.put( agntCnxn )( filter, mTT.Ground( ConcreteHL.PostedExpr( value ) ) )
                }
                
                handler( Some( mTT.Ground( ConcreteHL.Bottom ) ), Some(filter), Some(agntCnxn) )
              }
            }
          }
        }
      }
      
      trait MessageProcessorElements {
        self : Serializable =>
        def erql() : CnxnCtxtLabel[String,String,String]
        def rspLabelCtor() : String => CnxnCtxtLabel[String,String,String]
        def useBiLink() : Option[Boolean]
        def flip() : Boolean
      }

      trait MessageProcessor {
        self : MessageProcessorElements with Serializable =>

        def innerLoop(
          erql : CnxnCtxtLabel[String,String,String],
          client : LinkEvalRequestChannel,
          server : LinkEvalRequestChannel,
          node : StdEvalChannel,
          rspLabelCtor : String => CnxnCtxtLabel[String,String,String]
        ) : Unit = {
          BasicLogService.tweet(
            "entering method: innerLoop"
            + "\nthis: " + this
            + "\nerql: " + erql
            + "\nclient: " + client
            + "\nserver: " + server
            + "\nnode: " + node            
            + "\nrspLabelCtor: " + rspLabelCtor
          )
            reset { 
              for( e <- client.subscribe( erql ) ) {
                BasicLogService.tweet(
                  "method: innerLoop"
                  + "\n completed client.subscribe "
                  + "\nthis: " + this
                  + "\nerql: " + erql
                  + "\nclient: " + client
                  + "\nserver: " + server
                  + "\nnode: " + node
                  + "\n-----------------------------------------"
                  + "\ne: " + e
                )
                e match {
                  case Some( boundRsrc@DSLCommLink.mTT.RBoundAList( Some( DSLCommLink.mTT.Ground( expr ) ), subst ) ) => {
                    BasicLogService.tweet(
                      "method: innerLoop"
                      + "\n case rsrc type: DSLCommLink.mTT.RBoundAList"
                      + "\n completed client.subscribe "
                      + "\nthis: " + this
                      + "\nerql: " + erql
                      + "\nclient: " + client
                      + "\nserver: " + server
                      + "\nnode: " + node
                      + "\n-----------------------------------------"
                      + "\ne: " + e
                    )
                    for( map <- boundRsrc.sbst; CnxnCtxtLeaf( Left( sessionId ) ) <- map.get( "SessionId" ) ) {
                      val erspl : CnxnCtxtLabel[String,String,String] = rspLabelCtor( sessionId )
                      
                      val forward : Option[mTT.Resource] => Unit =
                        {
                          ( optRsrc : Option[mTT.Resource] ) => {
                            BasicLogService.tweet(
                              ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                              + "\nDiesel.scala:2258 forward(" + optRsrc + ")"
                              + "\n------------------------------------------------------------------"
                              + "\n defined in method innerLoop"
                              + "\n passed to and called in evaluateExpression"
                              + "\nerql: " + erql
                              + "\nserver: " + server                              
                              + "\n------------------------------------------------------------------"
                              + "\n erspl: " + erspl
                              + "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                            )
                            reset {
                              server.publish(
                                erspl,
                                DSLCommLink.mTT.Ground(
                                  optRsrc match {
                                    case None => {                                
                                      ConcreteHL.Bottom
                                    }
                                    case Some( mTT.Ground( v ) ) => {
                                      v
                                    }
                                    case Some( mTT.RBoundHM( Some( mTT.Ground( v ) ), _ ) ) => {
                                      v
                                    }
                                  }
                                )
                              )       
                            }
                          }
                        }
                      
                      evaluateExpression( node )( expr )( forward )
                    }             
                  }
                  case Some( boundRsrc@DSLCommLink.mTT.RBoundHM( Some( DSLCommLink.mTT.Ground( expr ) ), subst ) ) => {
                    BasicLogService.tweet(
                      "method: innerLoop"
                      + "\n case rsrc type: DSLCommLink.mTT.RBoundHM"
                      + "\n completed client.subscribe "
                      + "\nthis: " + this
                      + "\nerql: " + erql
                      + "\nclient: " + client
                      + "\nserver: " + server
                      + "\nnode: " + node
                      + "\n-----------------------------------------"
                      + "\ne: " + e
                    )
                    for( map <- boundRsrc.sbst; CnxnCtxtLeaf( Left( sessionId ) ) <- map.get( "SessionId" ) ) {
                      val erspl : CnxnCtxtLabel[String,String,String] = rspLabelCtor( sessionId )
                      
                      val forward : Option[mTT.Resource] => Unit =
                        {
                          ( optRsrc : Option[mTT.Resource] ) => {                            
                            BasicLogService.tweet(
                              ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                              + "\nDiesel.scala:2313 forward(" + optRsrc + ")"
                              + "\n------------------------------------------------------------------"
                              + "\n defined in method innerLoop"
                              + "\n passed to and called in evaluateExpression"
                              + "\nerql: " + erql
                              + "\nserver: " + server                              
                              + "\n------------------------------------------------------------------"
                              + "\n erspl: " + erspl
                              + "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                            )
                            reset {
                              server.publish(
                                erspl,
                                DSLCommLink.mTT.Ground(
                                  optRsrc match {
                                    case None => {                                
                                      ConcreteHL.Bottom
                                    }
                                    case Some( mTT.Ground( v ) ) => {
                                      v
                                    }
                                    case Some( mTT.RBoundHM( Some( mTT.Ground( v ) ), _ ) ) => {
                                      v
                                    }
                                  }
                                )
                              )       
                            }
                          }
                        }
                      
                      evaluateExpression( node )( expr )( forward )
                    }             
                  }
                  case Some( rsrc ) => {
                    rsrc match {
                      case boundRsrc@DSLCommLink.mTT.RBoundHM( innerOptRsrc, subst ) => {
                        BasicLogService.tweet(
                          "method: innerLoop"
                          + "\n case rsrc type: DSLCommLink.mTT.RBoundHM"
                          + "\n completed client.subscribe "
                          + "\nthis: " + this
                          + "\nerql: " + erql
                          + "\nclient: " + client
                          + "\nserver: " + server
                          + "\nnode: " + node
                          + "\n-----------------------------------------"
                          + "\ne: " + e
                        )
                        innerOptRsrc match {
                          case Some( DSLCommLink.mTT.Ground( expr ) ) => {
                            for( map <- boundRsrc.sbst; CnxnCtxtLeaf( Left( sessionId ) ) <- map.get( "SessionId" ) ) {
                              val erspl : CnxnCtxtLabel[String,String,String] = rspLabelCtor( sessionId )
                              
                              val forward : Option[mTT.Resource] => Unit =
                                {
                                  ( optRsrc : Option[mTT.Resource] ) => {
                                    BasicLogService.tweet(
                                      ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                      + "\nDiesel.scala:2372 forward(" + optRsrc + ")"
                                      + "\n------------------------------------------------------------------"
                                      + "\n defined in method innerLoop"
                                      + "\n passed to and called in evaluateExpression"
                                      + "\nerql: " + erql
                                      + "\nserver: " + server                              
                                      + "\n------------------------------------------------------------------"
                                      + "\n erspl: " + erspl
                                      + "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                    )
                                    reset {
                                      server.publish(
                                        erspl,
                                        DSLCommLink.mTT.Ground(
                                          optRsrc match {
                                            case None => {                                
                                              ConcreteHL.Bottom
                                            }
                                            case Some( mTT.Ground( v ) ) => {
                                              v
                                            }
                                            case Some( mTT.RBoundHM( Some( mTT.Ground( v ) ), _ ) ) => {
                                              v
                                            }
                                          }
                                        )
                                      )       
                                    }
                                  }
                                }                        
                              evaluateExpression( node )( expr )( forward )
                            }
                          }
                          case Some( innerRrsc ) => {
                            BasicLogService.tweet(
                              "method: innerLoop"
                              + "\n case unexpected inner rsrc type: " + innerRrsc
                              + "\ninner rsrc type: " + innerRrsc.getClass
                              + "\n completed client.subscribe "
                              + "\nthis: " + this
                              + "\nerql: " + erql
                              + "\nclient: " + client
                              + "\nserver: " + server
                              + "\nnode: " + node
                              + "\n-----------------------------------------"
                              + "\ne: " + e
                            )
                          }
                        }                      
                      }
                      case _ => {
                        BasicLogService.tweet(
                          "method: innerLoop"
                          + "\n case unexpected rsrc type: " + rsrc
                          + "\ninner rsrc type: " + rsrc.getClass
                          + "\n completed client.subscribe "
                          + "\nthis: " + this
                          + "\nerql: " + erql
                          + "\nclient: " + client
                          + "\nserver: " + server
                          + "\nnode: " + node
                          + "\n-----------------------------------------"
                          + "\ne: " + e
                        )
                      }
                    }             
                  }
                  case None => {
                    BasicLogService.tweet( "server loop waiting." )
                  }
                  case _ => {
                    BasicLogService.tweet(
                      "method: innerLoop"
                      + "\n rsrc not handled: " + e
                      + "\n completed client.subscribe "
                      + "\nthis: " + this
                      + "\nerql: " + erql
                      + "\nclient: " + client
                      + "\nserver: " + server
                      + "\nnode: " + node
                    )
                  }
                }
              }
            }
          //}          
          //loop()
        }

        def innerLoop(
          erql : CnxnCtxtLabel[String,String,String],
          client : LinkEvalRequestChannel,
          server : LinkEvalRequestChannel,
          node : String,
          rspLabelCtor : String => CnxnCtxtLabel[String,String,String]
        ) : Unit = {
          BasicLogService.tweet(
            "entering method: innerLoop"
            + "\nthis: " + this
            + "\nerql: " + erql
            + "\nclient: " + client
            + "\nserver: " + server
            + "\nnode: " + node            
            + "\nrspLabelCtor: " + rspLabelCtor
          )
            reset { 
              for( e <- client.subscribe( erql ) ) {
                e match {
                  case Some( boundRsrc@DSLCommLink.mTT.RBoundAList( Some( DSLCommLink.mTT.Ground( expr ) ), subst ) ) => {
                    BasicLogService.tweet(
                      "method: innerLoop"
                      + "\n completed client.subscribe "
                      + "\nthis: " + this
                      + "\nerql: " + erql
                      + "\nclient: " + client
                      + "\nserver: " + server
                      + "\nnode: " + node
                      + "\n-----------------------------------------"
                      + "\ne: " + e
                    )
                    for( map <- boundRsrc.sbst; CnxnCtxtLeaf( Left( sessionId ) ) <- map.get( "SessionId" ) ) {
                      val erspl : CnxnCtxtLabel[String,String,String] = rspLabelCtor( sessionId )
                      
                      val forward : (Option[mTT.Resource], Option[CnxnCtxtLabel[String,String,String]], Option[acT.AgentCnxn]) => Unit =
                        {
                          ( optRsrc, optFilter, optCnxn ) => {
                            BasicLogService.tweet(
                              ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                              + "\nDiesel.scala:2500 forward(" + optRsrc + ", " + optFilter + ", " + optCnxn + ")"
                              + "\n------------------------------------------------------------------"
                              + "\n defined in method innerLoop"
                              + "\n passed to and called in evaluateExpression"
                              + "\nerql: " + erql
                              + "\nserver: " + server                              
                              + "\n------------------------------------------------------------------"
                              + "\n erspl: " + erspl
                              + "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                            )
                            reset {
                              server.publish(
                                erspl,
                                DSLCommLink.mTT.Ground(
                                  optRsrc match {
                                    case None => {                                
                                      ConcreteHL.Bottom
                                    }
                                    case Some( mTT.Ground( v ) ) => {
                                      ConcreteHL.PostedExpr((v, optFilter.get, optCnxn.get))
                                    }
                                    case Some( mTT.RBoundHM( Some( mTT.Ground( v ) ), _ ) ) => {
                                      ConcreteHL.PostedExpr((v, optFilter.get, optCnxn.get))
                                    }
                                  }
                                )
                              )       
                            }
                          }
                        }
                      
                      evaluateExpression( node )( expr )( forward )
                    }             
                  }
                  case Some( boundRsrc@DSLCommLink.mTT.RBoundHM( Some( DSLCommLink.mTT.Ground( expr ) ), subst ) ) => {
                    BasicLogService.tweet(
                      "method: innerLoop"
                      + "\n case rsrc type: DSLCommLink.mTT.RBoundHM"
                      + "\n completed client.subscribe "
                      + "\nthis: " + this
                      + "\nerql: " + erql
                      + "\nclient: " + client
                      + "\nserver: " + server
                      + "\nnode: " + node
                      + "\n-----------------------------------------"
                      + "\ne: " + e
                    )
                    for( map <- boundRsrc.sbst; CnxnCtxtLeaf( Left( sessionId ) ) <- map.get( "SessionId" ) ) {
                      val erspl : CnxnCtxtLabel[String,String,String] = rspLabelCtor( sessionId )
                      
                      val forward : (Option[mTT.Resource], Option[CnxnCtxtLabel[String,String,String]], Option[acT.AgentCnxn]) => Unit =
                        {
                          ( optRsrc, optFilter, optCnxn ) => {
                            BasicLogService.tweet(
                              ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                              + "\nDiesel.scala:2555 forward(" + optRsrc + ", " + optFilter + ", " + optCnxn + ")"
                              + "\n------------------------------------------------------------------"
                              + "\n defined in method innerLoop"
                              + "\n passed to and called in evaluateExpression"
                              + "\nerql: " + erql
                              + "\nserver: " + server                              
                              + "\n------------------------------------------------------------------"
                              + "\n erspl: " + erspl
                              + "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                            )
                            reset {
                              server.publish(
                                erspl,
                                DSLCommLink.mTT.Ground(
                                  optRsrc match {
                                    case None => {                                
                                      ConcreteHL.Bottom
                                    }
                                    case Some( mTT.Ground( v ) ) => {
                                      ConcreteHL.PostedExpr((v, optFilter.get, optCnxn.get))
                                    }
                                    case Some( mTT.RBoundHM( Some( mTT.Ground( v ) ), _ ) ) => {
                                      ConcreteHL.PostedExpr((v, optFilter.get, optCnxn.get))
                                    }
                                  }
                                )
                              )       
                            }
                          }
                        }
                      
                      evaluateExpression( node )( expr )( forward )
                    }             
                  }
                  case Some( rsrc ) => {
                    rsrc match {
                      case boundRsrc@DSLCommLink.mTT.RBoundHM( innerOptRsrc, subst ) => {
                        BasicLogService.tweet(
                          "method: innerLoop"
                          + "\n case rsrc type: DSLCommLink.mTT.RBoundHM"
                          + "\n completed client.subscribe "
                          + "\nthis: " + this
                          + "\nerql: " + erql
                          + "\nclient: " + client
                          + "\nserver: " + server
                          + "\nnode: " + node
                          + "\n-----------------------------------------"
                          + "\ne: " + e
                        )
                        innerOptRsrc match {
                          case Some( DSLCommLink.mTT.Ground( expr ) ) => {
                            for( map <- boundRsrc.sbst; CnxnCtxtLeaf( Left( sessionId ) ) <- map.get( "SessionId" ) ) {
                              val erspl : CnxnCtxtLabel[String,String,String] = rspLabelCtor( sessionId )
                              
                              val forward : (Option[mTT.Resource], Option[CnxnCtxtLabel[String,String,String]], Option[acT.AgentCnxn]) => Unit =
                                {
                                  ( optRsrc, optFilter, optCnxn ) => {
                                    BasicLogService.tweet(
                                      ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                      + "\nDiesel.scala:2614 forward(" + optRsrc + ", " + optFilter + ", " + optCnxn + ")"
                                      + "\n------------------------------------------------------------------"
                                      + "\n defined in method innerLoop"
                                      + "\n passed to and called in evaluateExpression"
                                      + "\nerql: " + erql
                                      + "\nserver: " + server                              
                                      + "\n------------------------------------------------------------------"
                                      + "\n erspl: " + erspl
                                      + "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                    )
                                    reset {
                                      server.publish(
                                        erspl,
                                        DSLCommLink.mTT.Ground(
                                          optRsrc match {
                                            case None => {                                
                                              ConcreteHL.Bottom
                                            }
                                            case Some( mTT.Ground( v ) ) => {
                                              ConcreteHL.PostedExpr((v, optFilter.get, optCnxn.get))
                                            }
                                            case Some( mTT.RBoundHM( Some( mTT.Ground( v ) ), _ ) ) => {
                                              ConcreteHL.PostedExpr((v, optFilter.get, optCnxn.get))
                                            }
                                          }
                                        )
                                      )       
                                    }
                                  }
                                }                        
                              evaluateExpression( node )( expr )( forward )
                            }
                          }
                          case Some( innerRrsc ) => {
                            BasicLogService.tweet(
                              "method: innerLoop"
                              + "\n case unexpected inner rsrc type: " + innerRrsc
                              + "\ninner rsrc type: " + innerRrsc.getClass
                              + "\n completed client.subscribe "
                              + "\nthis: " + this
                              + "\nerql: " + erql
                              + "\nclient: " + client
                              + "\nserver: " + server
                              + "\nnode: " + node
                              + "\n-----------------------------------------"
                              + "\ne: " + e
                            )
                          }
                        }                      
                      }
                      case _ => {
                        BasicLogService.tweet(
                          "method: innerLoop"
                          + "\n case unexpected rsrc type: " + rsrc
                          + "\ninner rsrc type: " + rsrc.getClass
                          + "\n completed client.subscribe "
                          + "\nthis: " + this
                          + "\nerql: " + erql
                          + "\nclient: " + client
                          + "\nserver: " + server
                          + "\nnode: " + node
                          + "\n-----------------------------------------"
                          + "\ne: " + e
                        )
                      }
                    }             
                  }
                  case None => {
                    BasicLogService.tweet( "server loop waiting." )
                  }
                  case _ => {
                    BasicLogService.tweet(
                      "method: innerLoop"
                      + "\n rsrc not handled: " + e
                      + "\n completed client.subscribe "
                      + "\nthis: " + this
                      + "\nerql: " + erql
                      + "\nclient: " + client
                      + "\nserver: " + server
                      + "\nnode: " + node
                    )
                  }
                }
              }
            }
          //}          
          //loop()
        }

        def messageProcessorLoop(
          erql : CnxnCtxtLabel[String,String,String],
          node : StdEvalChannel,
          rspLabelCtor : String => CnxnCtxtLabel[String,String,String],
          useBiLink : Option[Boolean] = None,
          flip : Boolean = false
        ) : Unit = {
          val ( client, server ) = 
            useBiLink match {
              case Some( true ) => {
                DSLCommLinkCtor.stdBiLink()              
              }
              case Some( false ) => {
                val ( client, server ) = DSLCommLinkCtor.stdBiLink()
                ( server, client )
              }
              case None => {          
                val link = DSLCommLinkCtor.stdLink()( flip )
                ( link, link )
              }
            }
          innerLoop( erql, client, server, node, rspLabelCtor )
        }

        def lateMessageProcessorLoop(
          erql : CnxnCtxtLabel[String,String,String],
          node : String,
          rspLabelCtor : String => CnxnCtxtLabel[String,String,String],
          useBiLink : Option[Boolean] = None,
          flip : Boolean = false
        ) : Unit = {
          val ( client, server ) = 
            useBiLink match {
              case Some( true ) => {
                DSLCommLinkCtor.stdBiLink()              
              }
              case Some( false ) => {
                val ( client, server ) = DSLCommLinkCtor.stdBiLink()
                ( server, client )
              }
              case None => {          
                val link = DSLCommLinkCtor.stdLink()( flip )
                ( link, link )
              }
            }
          innerLoop( erql, client, server, node, rspLabelCtor )
        }

        def go( derefNodeEarly : Boolean = false ) : Unit = {
          throw new Exception( "attempting to run an abstract MessageProcessor" )
        }
      }      

      class MsgProcessorVals(
        @transient
        override val erql : CnxnCtxtLabel[String,String,String],
        @transient
        override val rspLabelCtor : String => CnxnCtxtLabel[String,String,String],
        override val useBiLink : Option[Boolean] = None,
        override val flip : Boolean = false
      ) extends MessageProcessorElements with Serializable {
        def this() = { this( null, null, None, false ) }
      }

      object MsgProcessorVals extends Serializable {
        def apply(
          erql : CnxnCtxtLabel[String,String,String],
          rspLabelCtor : String => CnxnCtxtLabel[String,String,String],
          useBiLink : Option[Boolean] = None,
          flip : Boolean = false
        ) : MsgProcessorVals = {
          new MsgProcessorVals( erql, rspLabelCtor, useBiLink, flip )
        }
        def unapply(
          mp : MsgProcessorVals
        ) : Option[
             (
               CnxnCtxtLabel[String,String,String],               
               String =>CnxnCtxtLabel[String,String,String],
               Option[Boolean],
               Boolean
             )
        ]
        = {
          Some( ( mp.erql, mp.rspLabelCtor, mp.useBiLink, mp.flip ) )
        }
      }

      case class MsgProcessor(
        @transient
        val node : StdEvalChannel,        
        @transient
        override val erql : CnxnCtxtLabel[String,String,String],
        @transient
        override val rspLabelCtor : String => CnxnCtxtLabel[String,String,String],
        override val useBiLink : Option[Boolean] = None,
        override val flip : Boolean = false
      ) extends MsgProcessorVals(
        erql, rspLabelCtor, useBiLink, flip
      ) with MessageProcessor with Serializable {
        def this() = { this( null, null, null, None, false ) }
        override def go( derefNodeEarly : Boolean = true ) : Unit = {
          if ( derefNodeEarly ) {
            messageProcessorLoop( erql, node, rspLabelCtor, useBiLink, flip )
          }
          else {
            BasicLogService.tweet( "warning: derefing node early anyway"  )
            messageProcessorLoop( erql, node, rspLabelCtor, useBiLink, flip )
          }
        }
      }

/*
      object MsgProcessor extends Serializable {
        def apply(
          node : StdEvalChannel,
          erql : CnxnCtxtLabel[String,String,String],
          rspLabelCtor : String => CnxnCtxtLabel[String,String,String],
          useBiLink : Option[Boolean] = None,
          flip : Boolean = false
        ) : MsgProcessor = {
          new MsgProcessor( node, erql, rspLabelCtor, useBiLink, flip )
        }
        def unapply(
          mp : MsgProcessor
        ) : Option[
             (
               StdEvalChannel,
               CnxnCtxtLabel[String,String,String],               
               String =>CnxnCtxtLabel[String,String,String],
               Option[Boolean],
               Boolean
             )
        ]
        = {
          Some( ( mp.node, mp.erql, mp.rspLabelCtor, mp.useBiLink, mp.flip ) )
        }
      }
 */

      case class IndirectMsgProcessor(
        val node : String,
        @transient
        override val erql : CnxnCtxtLabel[String,String,String],
        @transient
        override val rspLabelCtor : String => CnxnCtxtLabel[String,String,String],
        override val useBiLink : Option[Boolean] = None,
        override val flip : Boolean = false
      ) extends MsgProcessorVals(
        erql, rspLabelCtor, useBiLink, flip
      ) with MessageProcessor with Serializable {
        def this() = { this( null, null, null, None, false ) }
        override def go( derefNodeEarly : Boolean = false ) : Unit = {
          if ( derefNodeEarly ) {            
            for( n <- EvalNodeMapper.get( node ) ) {
              messageProcessorLoop( erql, n, rspLabelCtor, useBiLink, flip )
            }
          }
          else {
            lateMessageProcessorLoop( erql, node, rspLabelCtor, useBiLink, flip )
          }
        }
      }

/*
      object IndirectMsgProcessor extends Serializable {
        def apply(
          node : String,
          erql : CnxnCtxtLabel[String,String,String],
          rspLabelCtor : String => CnxnCtxtLabel[String,String,String],
          useBiLink : Option[Boolean] = None,
          flip : Boolean = false
        ) : IndirectMsgProcessor = {
          new IndirectMsgProcessor( node, erql, rspLabelCtor, useBiLink, flip )
        }
        def unapply(
          mp : IndirectMsgProcessor
        ) : Option[
             (
               String,
               CnxnCtxtLabel[String,String,String],
               String =>CnxnCtxtLabel[String,String,String],
               Option[Boolean],
               Boolean
             )
        ]
        = {
          Some( ( mp.node, mp.erql, mp.rspLabelCtor, mp.useBiLink, mp.flip ) )
        }
      }
*/

      case class MsgProcessorBlock(
        @transient
        override val self : List[MessageProcessor]
      ) extends scala.collection.SeqProxy[MessageProcessor] {
        def go() { for ( mp <- self ) { mp.go() } }
      }

      def adminLooper(
        node : StdEvalChannel,
        useBiLink : Option[Boolean] = None,
        flip : Boolean = false
      ) : MsgProcessor = {
        MsgProcessor(
          node,
          DSLCommLinkCtor.ExchangeLabels.adminRequestLabel()( Right[String,String]( "SessionId" ) ).getOrElse( 
            throw new Exception( "error making evalRequestLabel" )
          ),
          ( sessionId : String ) => {
            DSLCommLinkCtor.ExchangeLabels.adminResponseLabel()(
              Left[String,String]( sessionId )
            ).getOrElse( throw new Exception( "unable to make evaResponseLabel" ) )
          },
          useBiLink,
          flip
        )
      }

      def indirectAdminLooper(
        node : String,
        useBiLink : Option[Boolean] = None,
        flip : Boolean = false
      ) : IndirectMsgProcessor = {
        IndirectMsgProcessor(
          node,
          DSLCommLinkCtor.ExchangeLabels.adminRequestLabel()( Right[String,String]( "SessionId" ) ).getOrElse( 
            throw new Exception( "error making evalRequestLabel" )
          ),
          ( sessionId : String ) => {
            DSLCommLinkCtor.ExchangeLabels.adminResponseLabel()(
              Left[String,String]( sessionId )
            ).getOrElse( throw new Exception( "unable to make evaResponseLabel" ) )
          },
          useBiLink,
          flip
        )
      }

      def evalLooper(
        node : StdEvalChannel,
        useBiLink : Option[Boolean] = None,
        flip : Boolean = false
      ) : MsgProcessor = {
        MsgProcessor(
          node,
          DSLCommLinkCtor.ExchangeLabels.evalRequestLabel()( Right[String,String]( "SessionId" ) ).getOrElse( 
              throw new Exception( "error making evalRequestLabel" )
            ),
          ( sessionId : String ) => {
            DSLCommLinkCtor.ExchangeLabels.evalResponseLabel()(
              Left[String,String]( sessionId )
            ).getOrElse( throw new Exception( "unable to make evaResponseLabel" ) )
          },
          useBiLink,
          flip
        )
      }

      def indirectEvalLooper(
        node : String,
        useBiLink : Option[Boolean] = None,
        flip : Boolean = false
      ) : IndirectMsgProcessor = {
        IndirectMsgProcessor(
          node,
          DSLCommLinkCtor.ExchangeLabels.evalRequestLabel()( Right[String,String]( "SessionId" ) ).getOrElse( 
              throw new Exception( "error making evalRequestLabel" )
            ),
          ( sessionId : String ) => {
            DSLCommLinkCtor.ExchangeLabels.evalResponseLabel()(
              Left[String,String]( sessionId )
            ).getOrElse( throw new Exception( "unable to make evaResponseLabel" ) )
          },
          useBiLink,
          flip
        )
      }

      def stdLooper(
        //node : StdEvalChannel = agent( "/dieselProtocol" ),
        node : StdEvalChannel = dslEvaluatorAgent( ),
        useBiLink : Option[Boolean] = None,
        flip : Boolean = false
      ) : MsgProcessorBlock = {
        MsgProcessorBlock(
          List[MsgProcessor](
            adminLooper( node, useBiLink, flip ),
            evalLooper( node, useBiLink, flip )
          )
        )
      }
      
      def indirectStdLooper(
        node : String,
        useBiLink : Option[Boolean] = None,
        flip : Boolean = false
      ) : MsgProcessorBlock = {
        MsgProcessorBlock(
          List[MessageProcessor](
            indirectAdminLooper( node, useBiLink, flip ),
            indirectEvalLooper( node, useBiLink, flip )
          )
        )
      }
    }
  }

  object CommsLinkMapper extends MapProxy[String,DSLCommLinkCtor.StdEvaluationRequestChannel] {
    @transient
    override val self = new HashMap[String,DSLCommLinkCtor.StdEvaluationRequestChannel]()
  }

  object EvalNodeMapper extends MapProxy[String,DieselEngineCtor.StdEvalChannel] {
    @transient
    override val self = new HashMap[String,DieselEngineCtor.StdEvalChannel]()
  }

  object Server extends Serializable {
    lazy val helpMsg = 
      (
        "-help -- this message\n"
        + "config=<fileName>\n" 
      )
    def processArgs(
      args : Array[String]
    ) : HashMap[String,String] = {
      val map = new HashMap[String,String]()
      for( arg <- args ) {
        val argNVal = arg.split( "=" )
        if ( argNVal.size > 1 ) {
          ( argNVal( 0 ), argNVal( 1 ) ) match {
            case ( "config", file ) => {
              map += ( "config" -> file )
            }
          }
        }
        else {
          arg match {
            case "-help" => {
              BasicLogService.tweet( helpMsg )
            }
            case _ => {
              BasicLogService.tweet( "unrecognized arg: " + arg )
              BasicLogService.tweet( helpMsg )
            }
          }       
        }
      }
      map
    }

    @transient
    var _engine : Option[DieselEngineCtor.DieselEngine] = None
    def engine( s : Option[String] = Some( "eval.conf" ) ) : DieselEngineCtor.DieselEngine = {
      _engine match {
        case Some( e ) => e
        case None => {
          val e = new DieselEngineCtor.DieselEngine( s )
          _engine = Some( e )
          e
        }
      }
    }

    @transient
    var _looper : Option[DieselEngineCtor.DieselEngine#MsgProcessorBlock] = None
    def looper(
      e : DieselEngineCtor.DieselEngine = engine( None )
    ) : DieselEngineCtor.DieselEngine#MsgProcessorBlock = {
      _looper match {
        case Some( mpb ) => mpb
        case None => {
          val nodeId = UUID.randomUUID()
          val nodeKey = nodeId.toString
          //EvalNodeMapper += ( nodeKey -> DieselEngineCtor.agent( "/dieselProtocol" ) )
          EvalNodeMapper += ( nodeKey -> DieselEngineCtor.dslEvaluatorAgent( ) )
          val mpb = e.indirectStdLooper( nodeKey )
          _looper = Some( mpb )
          mpb
        }
      }
    }
    
    def run( args : Array[String] ) : Unit = {
      @transient
      val map = processArgs( args )
      @transient
      //val e = new DieselEngineCtor.DieselEngine( map.get( "config" ) )
      val e = engine( map.get( "config" ) )
      val version = e.version
      println( "*******************************************************" )
      println( "******************** Diesel engine ********************" )
      println( "******************** Version " + version + " ********************" )
      println( "*******************************************************" )
      
      //e.evalLoop()
      //e.adminLoop()
      //e.stdLooper().go()      
      looper( e ).go()
    }

    def run( ) : Unit = {
      val a1 = new Array[String]( 1 )
      a1( 0 ) = "config=eval.conf" 
      run( a1 )
    }
  }
}
