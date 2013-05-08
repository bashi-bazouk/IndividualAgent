// -*- mode: Scala;-*- 
// Filename:    EvalConfig.scala 
// Authors:     lgm                                                    
// Creation:    Thu May  2 21:55:34 2013 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.evaluator.spray

import com.protegra_ati.agentservices.store._

import com.biosimilarity.evaluator.distribution._
import com.biosimilarity.evaluator.msgs._
import com.biosimilarity.lift.model.store._
import com.biosimilarity.lift.lib._

import akka.actor._
import spray.routing._
import directives.CompletionMagnet
import spray.http._
import spray.http.StatusCodes._
import MediaTypes._

import spray.httpx.encoding._

import org.json4s._
import org.json4s.native.JsonMethods._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.continuations._ 

import com.typesafe.config._

import java.util.Date
import java.util.UUID

trait EvalConfig {
  var _config : Option[Config] = None
    
  def evalConfig() : Config = {
    _config match {
      case Some( cfg ) => cfg
      case None => {
        val cfg =
          ConfigFactory.load(
            ConfigFactory.parseFile(
              new java.io.File( "eval.conf" )
            )
          )
        _config = Some( cfg )
        cfg
      }
    }    
  }
}
