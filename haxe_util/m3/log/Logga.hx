package m3.log;

import m3.CrossMojo;
import m3.exception.Exception;
import m3.util.M;

using Lambda;
using m3.helper.StringHelper;

class Logga {
    
    var loggerLevel: LogLevel;
    var console: Dynamic;
    var statementPrefix: String;
    @:isVar public static var DEFAULT(get,null): Logga;
    private var initialized: Bool = false;

    public function new(logLevel: LogLevel) {
        this.loggerLevel = logLevel;
    }

    private function _getLogger(): Void {
        console = CrossMojo.windowConsole();
        initialized = true;
    }

    private static function get_DEFAULT(): Logga {
        if(DEFAULT == null) {
            DEFAULT = new Logga(LogLevel.DEBUG);
        }
        return DEFAULT;
    }

    public function setStatementPrefix(prefix: String): Void {
        this.statementPrefix = prefix;
    }

    public function log(statement: String, ?level: LogLevel, ?exception: Exception) {
        if(!initialized) {
            this._getLogger();
        }
        if(level == null) {
            level = LogLevel.INFO;
        }

        try {
            if(exception != null && exception.stackTrace != null && Reflect.isFunction(exception.stackTrace) ) {
                statement += "\n" + exception.stackTrace();
            }
        } catch (err: Dynamic) {
            log("Could not get stackTrace", LogLevel.ERROR);
        }

        if(statement.isBlank()) {
            this.console.error("empty log statement");
            this.console.trace();
        }

        if(statementPrefix.isNotBlank()) {
            statement = statementPrefix + " || " + statement;
        }

        // var console = CrossMojo.windowConsole();
        if(logsAtLevel(level) && console != null) {
            try {
                if( (Type.enumEq(level, LogLevel.TRACE) || Type.enumEq(level, LogLevel.DEBUG)) && console.debug != null) {
                    this.console.debug(statement);
                } else if (Type.enumEq(level, LogLevel.INFO) && console.info != null) {
                    this.console.info(statement);
                } else if (Type.enumEq(level, LogLevel.WARN) && console.warn != null) {
                    this.console.warn(statement);
                } else if (Type.enumEq(level, LogLevel.ERROR) && console.error != null) {
                    this.console.error(statement);
                    this.console.trace();
                } else {
                    this.console.log(statement);
                }
            } catch (err : Dynamic) {
                if(Reflect.hasField(M.getX(this.console, {}), "error")) {
                    this.console.error(err);
                }
            }
        }
    }
    public function logsAtLevel(level:LogLevel) {
        return Type.enumIndex(this.loggerLevel) <= Type.enumIndex(level);
    }
    public function setLogLevel(logLevel:LogLevel) {
        this.loggerLevel = logLevel;
    }
    public function trace(statement:String, ?exception:Exception) {
        log(statement, LogLevel.TRACE, exception);
    }
    public function debug(statement:String, ?exception:Exception) {
        log(statement, LogLevel.DEBUG, exception);
    }
    public function info(statement:String, ?exception:Exception) {
        log(statement, LogLevel.INFO, exception);
    }
    public function warn(statement:String, ?exception:Exception) {
        log(statement, LogLevel.WARN, exception);
    }
    public function error(statement:String, ?exception:Exception) {
        log(statement, LogLevel.ERROR, exception);
    }

    public static function getExceptionInst(err: Dynamic) {
        if(Std.is(err, Exception)) {
            return err;
        } else {
            return new Exception(err);
        }
    }
}



