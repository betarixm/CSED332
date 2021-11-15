grammar SimpleExpression;

PLUS   : '+' ;
MINUS  : '-' ;
MULT   : '*' ;
DIVIDE : '/' ;
EXPON  : '^' ;
   
LPAREN : '(' ;
RPAREN : ')' ;

NUMBER : '-'?[0-9]*'.'?[0-9]+([eE][-+]?[0-9]+)? ; 
ID     : 'x'[1-9][0-9]* ;
WS     : [ \r\t\u000C\n]+ -> skip ; 
 
exp
 : left=exp  op=EXPON          right=exp # binaryExpression
 | left=exp  op=(MULT|DIVIDE)  right=exp	# binaryExpression
 | left=exp  op=(PLUS|MINUS)   right=exp	# binaryExpression
 | LPAREN    sub=exp  RPAREN				# parenExpression
 | name=ID									# variable
 | value=NUMBER								# number
 ;

