<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="css/MainStyle.css">
<title>Gram&aacute;tica</title>
</head>
<body>
	<div id="align">
		<form action="Grammar" method="post">
			S&iacute;mbolo inicial: <input type="text"	size="1" 	maxlength="1"	name="initialSymbol"> <br/> <br/>
			Vari&aacute;veis: <input type="text" size="10" maxlength="20"	name="variables"> <br/> <br/>
			Terminais: <input type="text" size="10" maxlength="20"	name="terminals"> <br/> <br/>
			Palavra(Apenas para CYK): <input type="text"		size="20" 	maxlength=""	name="word"> </br></br>
			Regras de produ&ccedil;&atilde;o: <br/>
			<div id="align-textarea">				
				<textarea rows="12" cols="50" name="txtGrammar"></textarea> <br/><br/>
			<div id="align-button"><input type="submit" name="action"	value="S�mbolo inicial n�o recursivo" /></div>			
			<div id="align-button"><input type="submit" name="action"	value="Remo��o de s�mbolo l�mbda" /></div>
			<div id="align-button"><input type="submit" name="action"	value="Remo��o de regras da cadeia" /></div>
			<div id="align-button"><input type="submit" name="action"	value="Remo��o de regras inalcan��veis" /></div>
			<div id="align-button"><input type="submit" name="action"	value="Remo��o de regras n�o terminais" /></div>
			<div id="align-button"><input type="submit" name="action" 	value="Forma Normal de Chomsky" /></div>
			<div id="align-button"><input type="submit" name="action" 	value="Remo��o de recurs�o � esquerda imediata" /></div>
			<div id="align-button"><input type="submit" name="action" 	value="Remo��o de recurs�o � esquerda direta e indireta" /></div>
			<div id="align-button"><input type="submit" name="action" 	value="CYK" /></div>
			</div>		
		</form>
	</div>
</body>
</html>