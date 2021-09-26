grammar BooleanExp;

NOT : '!'  ;
AND : '&&' ;
OR  : '||' ;

TRUE  : 'true' ;
FALSE : 'false' ;

LPAREN : '(' ;
RPAREN : ')' ;

ID : 'p'[1-9][0-9]* ;
WS : [ \r\t\u000C\n]+ -> skip ;

expression
 : op=NOT  sub=expression					# unaryExp
 | left=expression  op=AND  right=expression	# binaryExp
 | left=expression  op=OR   right=expression	# binaryExp
 | LPAREN  sub=expression  RPAREN			# parenExp
 | name=ID								# variable
 | value=(TRUE|FALSE)					# constant
 ;