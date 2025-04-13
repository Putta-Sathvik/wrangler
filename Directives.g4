// Lexer rules
BYTE_SIZE : [0-9]+ ('.' [0-9]+)? BYTE_UNIT ;
TIME_DURATION : [0-9]+ ('.' [0-9]+)? TIME_UNIT ;

fragment BYTE_UNIT : [KkMmGgTt]? [Bb] ;
fragment TIME_UNIT : 'ms' | 's' | 'sec' | 'seconds' ;
