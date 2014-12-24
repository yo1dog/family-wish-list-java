package net.awesomebox.fwl.database;

/**
 * Contains constants for PostgreSQL SQLSTATE error codes.
 * 
 * @author michael.moore
 */
public final class PostgreSQLErrorCodes
{
	// CLASS 00 - SUCCESSFUL COMPLETION
	public static final String SUCCESSFUL_COMPLETION = "00000";
	
	// CLASS 01 - WARNING
	public static final String WARNING = "01000";
	public static final String DYNAMIC_RESULT_SETS_RETURNED = "0100C";
	public static final String IMPLICIT_ZERO_BIT_PADDING = "01008";
	public static final String NULL_VALUE_ELIMINATED_IN_SET_FUNCTION = "01003";
	public static final String PRIVILEGE_NOT_GRANTED = "01007";
	public static final String PRIVILEGE_NOT_REVOKED = "01006";
	public static final String STRING_DATA_RIGHT_TRUNCATION__WARNING = "01004";
	public static final String DEPRECATED_FEATURE = "01P01";
	
	// CLASS 02 - NO DATA (THIS IS ALSO A WARNING // CLASS PER THE SQL STANDARD)
	public static final String NO_DATA = "02000";
	public static final String NO_ADDITIONAL_DYNAMIC_RESULT_SETS_RETURNED = "02001";
	
	// CLASS 03 - SQL STATEMENT NOT YET COMPLETE
	public static final String SQL_STATEMENT_NOT_YET_COMPLETE = "03000";
	
	// CLASS 08 - CONNECTION EXCEPTION
	public static final String CONNECTION_EXCEPTION = "08000";
	public static final String CONNECTION_DOES_NOT_EXIST = "08003";
	public static final String CONNECTION_FAILURE = "08006";
	public static final String SQLCLIENT_UNABLE_TO_ESTABLISH_SQLCONNECTION = "08001";
	public static final String SQLSERVER_REJECTED_ESTABLISHMENT_OF_SQLCONNECTION = "08004";
	public static final String TRANSACTION_RESOLUTION_UNKNOWN = "08007";
	public static final String PROTOCOL_VIOLATION = "08P01";
	
	// CLASS 09 - TRIGGERED ACTION EXCEPTION
	public static final String TRIGGERED_ACTION_EXCEPTION = "09000";
	
	// CLASS 0A - FEATURE NOT SUPPORTED
	public static final String FEATURE_NOT_SUPPORTED = "0A000";
	
	// CLASS 0B - INVALID TRANSACTION INITIATION
	public static final String INVALID_TRANSACTION_INITIATION = "0B000";
	
	// CLASS 0F - LOCATOR EXCEPTION
	public static final String LOCATOR_EXCEPTION = "0F000";
	public static final String INVALID_LOCATOR_SPECIFICATION = "0F001";
	
	// CLASS 0L - INVALID GRANTOR
	public static final String INVALID_GRANTOR = "0L000";
	public static final String INVALID_GRANT_OPERATION = "0LP01";
	
	// CLASS 0P - INVALID ROLE SPECIFICATION
	public static final String INVALID_ROLE_SPECIFICATION = "0P000";
	
	// CLASS 21 - CARDINALITY VIOLATION
	public static final String CARDINALITY_VIOLATION = "21000";
	
	// CLASS 22 - DATA EXCEPTION
	public static final String DATA_EXCEPTION = "22000";
	public static final String ARRAY_SUBSCRIPT_ERROR = "2202E";
	public static final String CHARACTER_NOT_IN_REPERTOIRE = "22021";
	public static final String DATETIME_FIELD_OVERFLOW = "22008";
	public static final String DIVISION_BY_ZERO = "22012";
	public static final String ERROR_IN_ASSIGNMENT = "22005";
	public static final String ESCAPE_CHARACTER_CONFLICT = "2200B";
	public static final String INDICATOR_OVERFLOW = "22022";
	public static final String INTERVAL_FIELD_OVERFLOW = "22015";
	public static final String INVALID_ARGUMENT_FOR_LOGARITHM = "2201E";
	public static final String INVALID_ARGUMENT_FOR_POWER_FUNCTION = "2201F";
	public static final String INVALID_ARGUMENT_FOR_WIDTH_BUCKET_FUNCTION = "2201G";
	public static final String INVALID_CHARACTER_VALUE_FOR_CAST = "22018";
	public static final String INVALID_DATETIME_FORMAT = "22007";
	public static final String INVALID_ESCAPE_CHARACTER = "22019";
	public static final String INVALID_ESCAPE_OCTET = "2200D";
	public static final String INVALID_ESCAPE_SEQUENCE = "22025";
	public static final String NONSTANDARD_USE_OF_ESCAPE_CHARACTER = "22P06";
	public static final String INVALID_INDICATOR_PARAMETER_VALUE = "22010";
	public static final String INVALID_LIMIT_VALUE = "22020";
	public static final String INVALID_PARAMETER_VALUE = "22023";
	public static final String INVALID_REGULAR_EXPRESSION = "2201B";
	public static final String INVALID_TIME_ZONE_DISPLACEMENT_VALUE = "22009";
	public static final String INVALID_USE_OF_ESCAPE_CHARACTER = "2200C";
	public static final String MOST_SPECIFIC_TYPE_MISMATCH = "2200G";
	public static final String NULL_VALUE_NOT_ALLOWED = "22004";
	public static final String NULL_VALUE_NO_INDICATOR_PARAMETER = "22002";
	public static final String NUMERIC_VALUE_OUT_OF_RANGE = "22003";
	public static final String STRING_DATA_LENGTH_MISMATCH = "22026";
	public static final String STRING_DATA_RIGHT_TRUNCATION = "22001";
	public static final String SUBSTRING_ERROR = "22011";
	public static final String TRIM_ERROR = "22027";
	public static final String UNTERMINATED_C_STRING = "22024";
	public static final String ZERO_LENGTH_CHARACTER_STRING = "2200F";
	public static final String FLOATING_POINT_EXCEPTION = "22P01";
	public static final String INVALID_TEXT_REPRESENTATION = "22P02";
	public static final String INVALID_BINARY_REPRESENTATION = "22P03";
	public static final String BAD_COPY_FILE_FORMAT = "22P04";
	public static final String UNTRANSLATABLE_CHARACTER = "22P05";
	public static final String NOT_AN_XML_DOCUMENT = "2200L";
	public static final String INVALID_XML_DOCUMENT = "2200M";
	public static final String INVALID_XML_CONTENT = "2200N";
	public static final String INVALID_XML_COMMENT = "2200S";
	public static final String INVALID_XML_PROCESSING_INSTRUCTION = "2200T";
	
	// CLASS 23 - INTEGRITY CONSTRAINT VIOLATION
	public static final String INTEGRITY_CONSTRAINT_VIOLATION = "23000";
	public static final String RESTRICT_VIOLATION = "23001";
	public static final String NOT_NULL_VIOLATION = "23502";
	public static final String FOREIGN_KEY_VIOLATION = "23503";
	public static final String UNIQUE_VIOLATION = "23505";
	public static final String CHECK_VIOLATION = "23514";
	
	// CLASS 24 - INVALID CURSOR STATE
	public static final String INVALID_CURSOR_STATE = "24000";
	
	// CLASS 25 - INVALID TRANSACTION STATE
	public static final String INVALID_TRANSACTION_STATE = "25000";
	public static final String ACTIVE_SQL_TRANSACTION = "25001";
	public static final String BRANCH_TRANSACTION_ALREADY_ACTIVE = "25002";
	public static final String HELD_CURSOR_REQUIRES_SAME_ISOLATION_LEVEL = "25008";
	public static final String INAPPROPRIATE_ACCESS_MODE_FOR_BRANCH_TRANSACTION = "25003";
	public static final String INAPPROPRIATE_ISOLATION_LEVEL_FOR_BRANCH_TRANSACTION = "25004";
	public static final String NO_ACTIVE_SQL_TRANSACTION_FOR_BRANCH_TRANSACTION = "25005";
	public static final String READ_ONLY_SQL_TRANSACTION = "25006";
	public static final String SCHEMA_AND_DATA_STATEMENT_MIXING_NOT_SUPPORTED = "25007";
	public static final String NO_ACTIVE_SQL_TRANSACTION = "25P01";
	public static final String IN_FAILED_SQL_TRANSACTION = "25P02";
	
	// CLASS 26 - INVALID SQL STATEMENT NAME
	public static final String INVALID_SQL_STATEMENT_NAME = "26000";
	
	// CLASS 27 - TRIGGERED DATA CHANGE VIOLATION
	public static final String TRIGGERED_DATA_CHANGE_VIOLATION = "27000";
	
	// CLASS 28 - INVALID AUTHORIZATION SPECIFICATION
	public static final String INVALID_AUTHORIZATION_SPECIFICATION = "28000";
	
	// CLASS 2B - DEPENDENT PRIVILEGE DESCRIPTORS STILL EXIST
	public static final String DEPENDENT_PRIVILEGE_DESCRIPTORS_STILL_EXIST = "2B000";
	public static final String DEPENDENT_OBJECTS_STILL_EXIST = "2BP01";
	
	// CLASS 2D - INVALID TRANSACTION TERMINATION
	public static final String INVALID_TRANSACTION_TERMINATION = "2D000";
	
	// CLASS 2F - SQL ROUTINE EXCEPTION
	public static final String SQL_ROUTINE_EXCEPTION = "2F000";
	public static final String FUNCTION_EXECUTED_NO_RETURN_STATEMENT = "2F005";
	public static final String MODIFYING_SQL_DATA_NOT_PERMITTED = "2F002";
	public static final String PROHIBITED_SQL_STATEMENT_ATTEMPTED = "2F003";
	public static final String READING_SQL_DATA_NOT_PERMITTED = "2F004";
	
	// CLASS 34 - INVALID CURSOR NAME
	public static final String INVALID_CURSOR_NAME = "34000";
	
	// CLASS 38 - EXTERNAL ROUTINE EXCEPTION
	public static final String EXTERNAL_ROUTINE_EXCEPTION = "38000";
	public static final String CONTAINING_SQL_NOT_PERMITTED = "38001";
	public static final String MODIFYING_SQL_DATA_NOT_PERMITTED__EXTERNAL_ROUTINE = "38002";
	public static final String PROHIBITED_SQL_STATEMENT_ATTEMPTED__EXTERNAL_ROUTINE = "38003";
	public static final String READING_SQL_DATA_NOT_PERMITTED__EXTERNAL_ROUTINE = "38004";
	
	// CLASS 39 - EXTERNAL ROUTINE INVOCATION EXCEPTION
	public static final String EXTERNAL_ROUTINE_INVOCATION_EXCEPTION = "39000";
	public static final String INVALID_SQLSTATE_RETURNED = "39001";
	public static final String NULL_VALUE_NOT_ALLOWED__EXTERNAL_ROUTINE = "39004";
	public static final String TRIGGER_PROTOCOL_VIOLATED = "39P01";
	public static final String SRF_PROTOCOL_VIOLATED = "39P02";
	
	// CLASS 3B - SAVEPOINT EXCEPTION
	public static final String SAVEPOINT_EXCEPTION = "3B000";
	public static final String INVALID_SAVEPOINT_SPECIFICATION = "3B001";
	
	// CLASS 3D - INVALID CATALOG NAME
	public static final String INVALID_CATALOG_NAME = "3D000";
	
	// CLASS 3F - INVALID SCHEMA NAME
	public static final String INVALID_SCHEMA_NAME = "3F000";
	
	// CLASS 40 - TRANSACTION ROLLBACK
	public static final String TRANSACTION_ROLLBACK = "40000";
	public static final String TRANSACTION_INTEGRITY_CONSTRAINT_VIOLATION = "40002";
	public static final String SERIALIZATION_FAILURE = "40001";
	public static final String STATEMENT_COMPLETION_UNKNOWN = "40003";
	public static final String DEADLOCK_DETECTED = "40P01";
	
	// CLASS 42 - SYNTAX ERROR OR ACCESS RULE VIOLATION
	public static final String SYNTAX_ERROR_OR_ACCESS_RULE_VIOLATION = "42000";
	public static final String SYNTAX_ERROR = "42601";
	public static final String INSUFFICIENT_PRIVILEGE = "42501";
	public static final String CANNOT_COERCE = "42846";
	public static final String GROUPING_ERROR = "42803";
	public static final String INVALID_FOREIGN_KEY = "42830";
	public static final String INVALID_NAME = "42602";
	public static final String NAME_TOO_LONG = "42622";
	public static final String RESERVED_NAME = "42939";
	public static final String DATATYPE_MISMATCH = "42804";
	public static final String INDETERMINATE_DATATYPE = "42P18";
	public static final String WRONG_OBJECT_TYPE = "42809";
	public static final String UNDEFINED_COLUMN = "42703";
	public static final String UNDEFINED_FUNCTION = "42883";
	public static final String UNDEFINED_TABLE = "42P01";
	public static final String UNDEFINED_PARAMETER = "42P02";
	public static final String UNDEFINED_OBJECT = "42704";
	public static final String DUPLICATE_COLUMN = "42701";
	public static final String DUPLICATE_CURSOR = "42P03";
	public static final String DUPLICATE_DATABASE = "42P04";
	public static final String DUPLICATE_FUNCTION = "42723";
	public static final String DUPLICATE_PREPARED_STATEMENT = "42P05";
	public static final String DUPLICATE_SCHEMA = "42P06";
	public static final String DUPLICATE_TABLE = "42P07";
	public static final String DUPLICATE_ALIAS = "42712";
	public static final String DUPLICATE_OBJECT = "42710";
	public static final String AMBIGUOUS_COLUMN = "42702";
	public static final String AMBIGUOUS_FUNCTION = "42725";
	public static final String AMBIGUOUS_PARAMETER = "42P08";
	public static final String AMBIGUOUS_ALIAS = "42P09";
	public static final String INVALID_COLUMN_REFERENCE = "42P10";
	public static final String INVALID_COLUMN_DEFINITION = "42611";
	public static final String INVALID_CURSOR_DEFINITION = "42P11";
	public static final String INVALID_DATABASE_DEFINITION = "42P12";
	public static final String INVALID_FUNCTION_DEFINITION = "42P13";
	public static final String INVALID_PREPARED_STATEMENT_DEFINITION = "42P14";
	public static final String INVALID_SCHEMA_DEFINITION = "42P15";
	public static final String INVALID_TABLE_DEFINITION = "42P16";
	public static final String INVALID_OBJECT_DEFINITION = "42P17";
	
	// CLASS 44 - WITH CHECK OPTION VIOLATION
	public static final String WITH_CHECK_OPTION_VIOLATION = "44000";
	
	// CLASS 53 - INSUFFICIENT RESOURCES
	public static final String INSUFFICIENT_RESOURCES = "53000";
	public static final String DISK_FULL = "53100";
	public static final String OUT_OF_MEMORY = "53200";
	public static final String TOO_MANY_CONNECTIONS = "53300";
	
	// CLASS 54 - PROGRAM LIMIT EXCEEDED
	public static final String PROGRAM_LIMIT_EXCEEDED = "54000";
	public static final String STATEMENT_TOO_COMPLEX = "54001";
	public static final String TOO_MANY_COLUMNS = "54011";
	public static final String TOO_MANY_ARGUMENTS = "54023";
	
	// CLASS 55 - OBJECT NOT IN PREREQUISITE STATE
	public static final String OBJECT_NOT_IN_PREREQUISITE_STATE = "55000";
	public static final String OBJECT_IN_USE = "55006";
	public static final String CANT_CHANGE_RUNTIME_PARAM = "55P02";
	public static final String LOCK_NOT_AVAILABLE = "55P03";
	
	// CLASS 57 - OPERATOR INTERVENTION
	public static final String OPERATOR_INTERVENTION = "57000";
	public static final String QUERY_CANCELED = "57014";
	public static final String ADMIN_SHUTDOWN = "57P01";
	public static final String CRASH_SHUTDOWN = "57P02";
	public static final String CANNOT_CONNECT_NOW = "57P03";
	
	// CLASS 58 - SYSTEM ERROR (ERRORS EXTERNAL TO POSTGRESQL ITSELF)
	public static final String IO_ERROR = "58030";
	public static final String UNDEFINED_FILE = "58P01";
	public static final String DUPLICATE_FILE = "58P02";
	
	// CLASS F0 - CONFIGURATION FILE ERROR
	public static final String CONFIG_FILE_ERROR = "F0000";
	public static final String LOCK_FILE_EXISTS = "F0001";
	
	// CLASS P0 - PL/PGSQL ERROR
	public static final String PLPGSQL_ERROR = "P0000";
	public static final String RAISE_EXCEPTION = "P0001";
	public static final String NO_DATA_FOUND = "P0002";
	public static final String TOO_MANY_ROWS = "P0003";
	
	// CLASS XX - INTERNAL ERROR
	public static final String INTERNAL_ERROR = "XX000";
	public static final String DATA_CORRUPTED = "XX001";
	public static final String INDEX_CORRUPTED = "XX002";
}
