package vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GrammarParser {

	private GrammarParser() {
	}

	public static Set<String> extractVariablesFromFull(String txt) {
		Set<String> Variables = new HashSet<>();

		// search for the variables
		String[] variables = new String[txt.length()];
		int j = 0;
		for (int i = 0; i < txt.length(); i++) {
			if (Character.isUpperCase(txt.charAt(i))) {
				variables[j] = Character.toString(txt.charAt(i));
				j++;
			}
		}

		for (String element : variables) {
			if (element != null)
				Variables.add(element);
		}

		return Variables;
	}

	public static Set<String> extractTerminalsFromFull(String txt) {
		Set<String> Terminals = new HashSet<>();

		// search for the terminals
		String[] terminals = new String[txt.length()];
		int j = 0;
		for (int i = 0; i < txt.length(); i++) {
			if (Character.isLowerCase(txt.charAt(i))) {
				terminals[j] = Character.toString(txt.charAt(i));
				j++;
			}
		}

		for (String element : terminals) {
			if (element != null)
				Terminals.add(element);
		}

		return Terminals;
	}

	public static Set<Rule> extractRulesFromFull(String txt) {
		Set<Rule> rule;
		rule = new HashSet<Rule>();
		Rule r = new Rule();

		// get the rules
		String[] rules = txt.split("\n");

		String[] auxRule;
		for (String x : rules) {
			auxRule = x.split("->");
			r.setleftSide(auxRule[0].trim());
			r.setrightSide(auxRule[1].trim());
			rule.add(new Rule(r));
		}
		return rule;
	}

	public static String extractInitialSymbolFromFull(String txt) {
		return Character.toString(txt.charAt(0));
	}

	public static boolean compareInitialSymbolWithParameter(
			String initialSymbol, String rules) {
		for (int i = 0; i < rules.length(); i++) {
			if (initialSymbol.equals(rules.charAt(i)))
				return true;
		}
		return false;
	}

	public static boolean compareSymbolWithParameter(String variables,
			String rules) {
		int i = 0;
		boolean search = false;
		while (i < variables.length()) {
			for (int j = 0; j < rules.length() && search == false; j++) {
				if (variables.charAt(i) == rules.charAt(j))
					search = true;
			}
			if (search == false)
				return false;
			else
				search = false;
			i++;
		}
		return true;
	}

	public static Set<String> formatTerminals(String terminals) {
		Set<String> terminalsFormated = new HashSet<>();
		for (int i = 0; i < terminals.length(); i++) {
			if (Character.isLowerCase(terminals.charAt(i)))
				terminalsFormated.add(Character.toString(terminals.charAt(i)));
		}
		return terminalsFormated;
	}

	public static Set<String> formatVariables(String variables) {
		Set<String> variablesFormated = new HashSet<>();

		for (int i = 0; i < variables.length(); i++) {
			if (Character.isUpperCase(variables.charAt(i)))
				variablesFormated.add(Character.toString(variables.charAt(i)));
		}
		return variablesFormated;
	}

	public static String formatInitialSymbol(String initialSymbol) {
		return Character
				.toString(Character.toUpperCase(initialSymbol.charAt(0)));
	}

	// retira símbolo inicial recursivo
	// retorna gramática sem símbolo inicial recursivo
	public static Grammar getGrammarWithInitialSymbolNotRecursive(Grammar g) {
		String initialSymbol = g.getInitialSymbol();
		String auxLeft = new String();
		String auxRight = new String();
		Iterator it = g.getRule().iterator();
		boolean insert = false;
		for (Rule element : g.getRule()) {
			if (element.getleftSide().equals(initialSymbol)) {
				if (element.getrightSide().contains(initialSymbol)) {
					auxLeft = element.getleftSide();
					auxRight = element.getrightSide();
					insert = true;
				}
			}
		}
		if (insert == true) {
			g.insertRule(initialSymbol + "'", initialSymbol);
			g.setInitialSymbol(initialSymbol + "'");
		}
		// g.removeRule(auxLeft, auxRight);
		/*
		 * String teste = aux.replace(initialSymbol,' '); //Iterator it =
		 * g.getRule().iterator(); while (it.hasNext()) { Rule r = (Rule)
		 * it.next(); if (r.getrightSide().equals(aux)) it.remove(); }
		 */
		return g;
	}

	public static String searchVariablesOnRules(String element) {
		String containsVariables = new String();
		boolean found = false;
		String[] verifyRules = element.split(" | ");
		for (String aux : verifyRules) {
			for (int i = 0; i < aux.length(); i++) {
				if (Character.isLowerCase(aux.charAt(i)))
					found = true;
			}
			if (found == false) {
				for (int i = 0; i < aux.length(); i++) {
					if (Character.isUpperCase(aux.charAt(i)))
						containsVariables += Character.toString(aux.charAt(i));
				}
			}
			found = false;
		}
		return containsVariables;
	}

	public static boolean searchVariables(String variables, Set<String> prev) {
		boolean found = false;
		for (int i = 0; i < variables.length() && found == false; i++) {
			if (prev.contains(Character.toString(variables.charAt(i))))
				found = true;
		}
		return found;
	}

	public static String replaceEmpty(String aux, Rule element) {
		/*
		 * for (int i = 0; i < aux.length(); i++) { if (aux.charAt(i) == '.') }
		 */
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(aux);
		for (int i = 0; i < stringBuilder.length(); i++) {
			if (stringBuilder.charAt(i) == '.')
				stringBuilder.setCharAt(i,
						Character.toLowerCase(element.getleftSide().charAt(0)));
		}
		// aux.replaceAll(".", "d");
		// Character.toString(Character.toLowerCase(element.getleftSide().charAt(0)))
		aux = stringBuilder.toString();
		return aux;
	}

	public static String permutation(String variables, int index, String aux) {
		String newSentence = new String();
		if (aux.length() != 1) {
			newSentence = " | ";
			for (int i = 0; i < aux.length(); i++) {
				if (i != index)
					newSentence += Character.toString(aux.charAt(i));
			}
		}
		return newSentence;
	}

	public static String cancelsVariables(String variables,
			Set<String> nullableVariables) {
		String[] searchVariables = variables.split(" | ");
		for (String aux : searchVariables) {
			for (int i = 0; i < aux.length(); i++) {
				if (nullableVariables
						.contains(Character.toString(aux.charAt(i)))) {
					// se a variável que estou percorrendo é anulável, deve
					// ocorrer a permutação
					variables += permutation(variables, i, aux);
				}
			}
		}
		return variables;
	}

	// remove símbolos terminais de regras
	// retorna gramática essencialmente não-contrátil
	public static Grammar getGrammarEssentiallyNoncontracting(Grammar g) {
		// conjunto que irá armazenar o conjunto de variáveis anuláveis
		Set<String> nullableVariables = new HashSet<>();
		Set<String> nullableVariablesAux = new HashSet<>();
		// conjunto que irá armazenar o conjunto de próximas variáveis
		Set<String> prev = new HashSet<>();
		// percorre todas as regras procurando alguma variável que produza vazio
		for (Rule element : g.getRule()) {
			if (element.getrightSide().contains(Character.toString('.')))
				nullableVariables.add(element.getleftSide());
		}
		// calcula conjunto de variáveis anuláveis
		do {
			prev.addAll(nullableVariables);
			for (Rule element : g.getRule()) {
				String containsVariables = searchVariablesOnRules(element
						.getrightSide());
				if (searchVariables(containsVariables, prev)) {
					nullableVariables.add(element.getleftSide());
				}
			}
		} while (!nullableVariables.equals(prev));
		nullableVariablesAux.addAll(nullableVariables);
		// realiza comparações
		// procura vazio
		Grammar teste = new Grammar();
		for (Rule element : g.getRule()) {
			// verifica se variável está no conjunto anulável e se
			// contém o símbolo lâmbda
			if (nullableVariablesAux.contains(element.getleftSide())
					&& element.getrightSide().contains(".")) {
				String aux = element.getrightSide();
				aux = replaceEmpty(aux, element);
				teste.insertRule(element.getleftSide(), aux);
				nullableVariablesAux.remove(element.getleftSide());
			} else {
				teste.insertRule(element.getleftSide(), element.getrightSide());
			}
		}
		g.setRule(teste.getRule());
		/*
		 * for (Rule element : g.getRule()) {
		 * System.out.print(element.getleftSide()); System.out.print(" -> ");
		 * System.out.print(element.getrightSide()); System.out.println(); }
		 * System.out.println("---------------------");
		 */
		Grammar teste2 = new Grammar();
		for (Rule element : g.getRule()) {
			if (nullableVariablesAux.contains(element.getleftSide())) {
				// preciso trabalhar com variáveis do lado direito
				String aux = element.getrightSide();
				aux = cancelsVariables(aux, nullableVariables);
				teste2.insertRule(element.getleftSide(), aux);
			} else {
				teste2.insertRule(element.getleftSide(), element.getrightSide());
			}
		}
		g.setRule(teste2.getRule());
		if (nullableVariables.contains(g.getInitialSymbol())) {
			Grammar teste3 = new Grammar();
			for (Rule element : g.getRule()) {
				if (element.getleftSide().equals(g.getInitialSymbol())) {
					teste3.insertRule(element.getleftSide(),
							element.getrightSide() + " | .");
				} else {
					teste3.insertRule(element.getleftSide(),
							element.getrightSide());
				}
			}
			g.setRule(teste3.getRule());
		}
		return g;
	}

	// verifica se a sentença possui regra da cadeia
	public static boolean isChain(String[] auxRightSide) {
		boolean chain = false;
		for (int i = 0; i < auxRightSide.length && chain == false; i++) {
			if (auxRightSide[i].length() == 1 && !auxRightSide[i].contains("|")) {
				if (Character.isUpperCase(auxRightSide[i].charAt(0)))
					chain = true;
			}
		}
		return chain;
	}

	public static boolean foundChainRules(char caracter, String s,
			ArrayList<Rule> noChainRules) {
		// pesquisa se o caracter está no conjunto direto
		boolean found = false;
		for (int i = 0; i < noChainRules.size() && found == false; i++) {
			if (noChainRules.get(i).getleftSide()
					.equals(Character.toString(caracter))) {
				s += noChainRules.get(i).getrightSide() + " ";
				// CONTINUAR AQUI
				found = true;
			}
		}
		return found;
	}

	public static String replaceChainRules(char caracter, String s,
			ArrayList<Rule> noChainRules) {
		boolean found = false;
		for (int i = 0; i < noChainRules.size() && found == false; i++) {
			if (noChainRules.get(i).getleftSide()
					.equals(Character.toString(caracter))) {
				s += noChainRules.get(i).getrightSide() + " ";
				// CONTINUAR AQUI
				found = true;
			}
		}
		return s;
	}

	public static void searchChainRules(String[] aux,
			ArrayList<Rule> noChainRules, ArrayList<Rule> chainRules, Rule r) {
		String s = new String();
		String leftSide = new String();
		for (int i = 0; i < aux.length; i++) {
			if (aux[i].length() == 1 && !aux[i].contains("|")
					&& Character.isUpperCase(aux[i].charAt(0))) {
				// primeiramente deve-se verificar se a regra da cadeia pode ser
				// resolvida diretamente
				if (foundChainRules(aux[i].charAt(0), s, noChainRules)) {
					// se a regra da cadeia puder ser selecionada imediatamente
					s = replaceChainRules(aux[i].charAt(0), s, noChainRules);
				}
			} else {
				s += aux[i] + " ";
			}
		}
		r.setrightSide(s);
	}

	public static int returnIndex(ArrayList<Rule> chainRules, String leftside) {
		int i = 0;
		boolean aux = false;
		for (; i < chainRules.size() && aux == false; i++) {
			if (chainRules.get(i).getleftSide().equals(leftside))
				aux = true;
		}
		return i - 1;
	}

	// remove regras da cadeia
	// retorna gramática sem regras da cadeia
	public static Grammar getGrammarWithoutChainRules(Grammar g) {
		// primeiro passo, dividir as regras em dois conjuntos: possui chain
		// rules
		// e não possui chain rules
		ArrayList<Rule> noChainRules = new ArrayList<Rule>();
		ArrayList<Rule> chainRules = new ArrayList<Rule>();
		// padronizando a gramática
		for (Rule element : g.getRule()) {
			System.out.println(element.getrightSide());
			String[] auxRightSide = element.getrightSide().split(" | ");
			for (int i = 0; i < auxRightSide.length; i++) {
				auxRightSide[i] = auxRightSide[i].trim();
			}
			if (isChain(auxRightSide)) {
				Rule r = new Rule();
				r.setleftSide(element.getleftSide());
				r.setrightSide(element.getrightSide());
				chainRules.add(r);
			} else {
				Rule r = new Rule();
				r.setleftSide(element.getleftSide());
				r.setrightSide(element.getrightSide());
				noChainRules.add(r);
			}
		}
		// enquanto houver chain rules
		while (chainRules.size() != 0) {
			System.out.println(chainRules.size());
			Rule r = new Rule();
			for (int i = 0; i < chainRules.size(); i++) {
				String[] aux = chainRules.get(i).getrightSide().split(" | ");
				for (int j = 0; j < aux.length; j++) {
					aux[j] = aux[j].trim();
				}
				r.setleftSide(chainRules.get(i).getleftSide());
				searchChainRules(aux, noChainRules, chainRules, r);
				System.out.println(r.getleftSide() + " -> " + r.getrightSide());
				// concertar o loop
				chainRules.remove(returnIndex(chainRules, r.getleftSide()));
				for (int j = 0; j < chainRules.size(); j++)
					System.out.println(chainRules.get(j).getleftSide() + " -> "
							+ chainRules.get(j).getrightSide());
				System.out.println(chainRules.size());
			}
			noChainRules.add(r);
		}
		System.out.println("--------------------");
		for (int j = 0; j < noChainRules.size(); j++)
			System.out.println(noChainRules.get(j).getleftSide() + " -> "
					+ noChainRules.get(j).getrightSide());
		// utilizando gramática auxiliar para armazenar novas regras
		Grammar g2 = new Grammar();
		// copia elementos do ArrayList na gramática auxiliar
		for (int i = 0; i < noChainRules.size(); i++) {
			g2.insertRule(noChainRules.get(i).getleftSide(), noChainRules
					.get(i).getrightSide());
			;
		}
		// copia gramática auxiliar na gramática principal
		g.setRule(g2.getRule());
		for (Rule element : g.getRule()) {
			System.out.println(element.getleftSide() + " -> "
					+ element.getrightSide());
		}
		return g;
	}

	// atualiza as regras da gramática após rodar algoritmos de remoção
	// de símbolos inúteis
	public static Set<Rule> updateRules(Set<String> prev, Grammar g) {
		Set<Rule> newRules = new HashSet<>();
		for (Rule element : g.getRule()) {
			if (prev.contains(element.getleftSide())) {
				String newRule = new String();
				String[] aux = element.getrightSide().split(" | ");
				for (int i = 0; i < aux.length; i++) {
					aux[i] = aux[i].trim();
					boolean insertOnNewRule = true;
					for (int j = 0; j < aux[i].length()
							&& insertOnNewRule != false; j++) {
						if (Character.isUpperCase(aux[i].charAt(j))) {
							if (prev.contains(Character.toString(aux[i]
									.charAt(j))))
								insertOnNewRule = true;
							else
								insertOnNewRule = false;
						} else if (Character.isLowerCase(aux[i].charAt(j))) {
							insertOnNewRule = true;
						} else {
							insertOnNewRule = false;
						}
					}
					if (insertOnNewRule) {
						newRule += aux[i] + " | ";
					}
				}
				if (newRule.length() != 0) {
					Rule r = new Rule();
					r.setleftSide(element.getleftSide());
					r.setrightSide(newRule);
					newRules.add(r);
				}
			}
		}
		return newRules;
	}

	// atualiza os terminais da gramática após remover variáveis inúteis
	public static Set<String> updateTerminals(Grammar g) {
		Set<String> newTerminals = new HashSet<>();
		for (Rule element : g.getRule()) {
			for (int i = 0; i < element.getrightSide().length(); i++) {
				if (Character.isLowerCase(element.getrightSide().charAt(i)))
					newTerminals.add(Character.toString(element.getrightSide()
							.charAt(i)));
			}
		}
		return newTerminals;
	}

	public static Grammar getGrammarWithoutNoTerm(Grammar g) {
		Set<String> term = new HashSet<>();
		Set<String> prev = new HashSet<>();
		// preenche conjunto term com as variáveis que são terminais
		for (Rule element : g.getRule()) {
			String[] aux = element.getrightSide().split(" | ");
			for (int i = 0; i < aux.length; i++) {
				aux[i] = aux[i].trim();
				if (aux[i].length() == 1 && (!aux[i].equals("|"))
						&& Character.isLowerCase(aux[i].charAt(0))) {
					term.add(element.getleftSide());
				}
			}
		}
		do {
			prev.addAll(term);
			for (Rule element : g.getRule()) {
				String[] aux = element.getrightSide().split(" | ");
				for (int i = 0; i < aux.length; i++) {
					aux[i] = aux[i].trim();
				}
				boolean insertOnTerm = false;
				for (int i = 0; i < aux.length && insertOnTerm == false; i++) {
					if (aux[i].length() == 1) {
						if (g.getTerminals().contains(
								Character.toString(aux[i].charAt(0)))
								|| prev.contains(Character.toString(aux[i]
										.charAt(0)))) {
							insertOnTerm = true;
						}
					} else {
						for (int j = 0; j < aux[i].length(); j++) {
							if (g.getTerminals().contains(
									Character.toString(aux[i].charAt(j)))
									|| prev.contains(Character.toString(aux[i]
											.charAt(j)))) {
								insertOnTerm = true;
							} else {
								insertOnTerm = false;
							}
						}
					}
				}
				if (insertOnTerm) {
					term.add(element.getleftSide());
				}
			}
		} while (!term.equals(prev));
		Grammar aux = new Grammar();
		aux.setVariables(prev);
		aux.setInitialSymbol(g.getInitialSymbol());
		aux.setRule(updateRules(prev, g));
		aux.setTerminals(updateTerminals(aux));
		g.setVariables(aux.getVariables());
		g.setTerminals(aux.getTerminals());
		g.setRule(aux.getRule());
		return g;
	}

	public static Set<String> reachMinusPrev(Set<String> reach, Set<String> prev) {
		Set<String> aux = new HashSet<>();
		for (String element : reach) {
			if (!prev.contains(element)) {
				aux.add(element);
			}
		}
		return aux;
	}

	public static Set<String> variablesInW(Set<String> reach, String rightSide) {
		for (int i = 0; i < rightSide.length(); i++) {
			if (Character.isUpperCase(rightSide.charAt(i)))
				reach.add(Character.toString(rightSide.charAt(i)));
		}
		return reach;
	}

	public static Grammar getGrammarWithoutNoReach(Grammar g) {
		Set<String> reach = new HashSet<>();
		Set<String> prev = new HashSet<>();
		Set<String> New = new HashSet<>();
		reach.add(g.getInitialSymbol());
		do {
			New.addAll(reachMinusPrev(reach, prev));
			prev.addAll(reach);
			for (String element : New) {
				for (Rule secondElement : g.getRule()) {
					if (secondElement.getleftSide().equals(element)) {
						reach.addAll(variablesInW(reach,
								secondElement.getrightSide()));
					}
				}
			}
		} while (!reach.equals(prev));
		Grammar aux = new Grammar();
		aux.setVariables(prev);
		aux.setInitialSymbol(g.getInitialSymbol());
		aux.setRule(updateRules(prev, g));
		aux.setTerminals(updateTerminals(aux));
		g.setVariables(aux.getVariables());
		g.setTerminals(aux.getTerminals());
		g.setRule(aux.getRule());
		return g;
	}

	public static boolean existsProduction(String leftSide, String symbol,
			Grammar g, Set<Rule> newSetOfRules) {
		for (Rule element : g.getRule()) {
			if (!element.getleftSide().equals(leftSide)) {
				String[] aux = element.getrightSide().split(" | ");
				for (int i = 0; i < aux.length; i++) {
					if (aux[i].equals(symbol)) {
						return true;
					}
				}
			}
		}
		for (Rule element : newSetOfRules) {
			if (!element.getleftSide().equals(leftSide)) {
				String[] aux = element.getrightSide().split(" | ");
				for (int i = 0; i < aux.length; i++) {
					if (aux[i].equals(symbol)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String getVariable(String symbol, Grammar g,
			Set<Rule> newSetOfRules) {
		String variable = new String();
		boolean found = false;
		for (Rule element : g.getRule()) {
			String[] aux = element.getrightSide().split(" | ");
			for (int i = 0; i < aux.length && found == false; i++) {
				aux[i] = aux[i].trim();
				if (aux[i].equals(symbol)) {
					variable = element.getleftSide();
					found = true;
				}
			}
		}
		for (Rule element : newSetOfRules) {
			String[] aux = element.getrightSide().split(" | ");
			for (int i = 0; i < aux.length && found == false; i++) {
				aux[i] = aux[i].trim();
				if (aux[i].equals(symbol)) {
					variable = element.getleftSide();
					found = true;
				}
			}
		}
		return variable;
	}

	private static int counterLowerCaseLetters(String sentence) {
		int count = 0;
		boolean valid = true;
		for (int i = 1; i < sentence.length() && valid == true; i++) {
			if (Character.isUpperCase(sentence.charAt(i)))
				valid = false;
			else
				count++;
		}
		return count;
	}

	private static String insertRightRide(String sentence, int contInsertions,
			Set<Rule> newSetOfRules, Grammar g) {
		String aux = new String();
		if (sentence.length() == 2
				&& Character.isUpperCase(sentence.charAt(0))
				&& Character
						.isLowerCase(sentence.charAt(sentence.length() - 1))) {
			aux = sentence.substring(0, sentence.length() - 1) + "T"
					+ (contInsertions);
		} else if (Character.isDigit(sentence.charAt(1))
				&& sentence.length() > 2) {
			aux = Character.toString(sentence.charAt(0))
					+ Character.toString(sentence.charAt(1)) + "T"
					+ (contInsertions + 1);
		} else if (Character.isLowerCase(sentence.charAt(1))
				&& (Character.isLowerCase(sentence.charAt(2)))
				&& sentence.length() > 2) {
			newSetOfRules = insertNewRules(newSetOfRules, sentence,
					contInsertions, g);
			contInsertions = updateNumberOfInsertions(newSetOfRules,
					contInsertions);
			aux = Character.toString(sentence.charAt(0)) + "T" + contInsertions;
		} else if (sentence.length() > 2)
			aux = Character.toString(sentence.charAt(0)) + "T"
					+ (contInsertions + 1);
		else if (Character.isLowerCase(sentence.charAt(1))
				&& sentence.length() == 2)
			aux = Character.toString(sentence.charAt(0)) + "T"
					+ (contInsertions + 1);
		else
			aux = sentence;
		return aux;
	}

	private static int updateNumberOfInsertions(Set<Rule> newSetOfRules,
			int contInsertions) {
		int counter = 0;
		for (Rule element : newSetOfRules) {
			if (element.getleftSide().contains("T")) {
				String aux = element.getleftSide().substring(1);
				if (Integer.parseInt(aux) > counter)
					counter = Integer.parseInt(aux);
			}
		}
		return (counter + 1);
	}

	private static Set<Rule> insertNewRules(Set<Rule> newSetOfRules,
			String sentence, int contInsertions, Grammar g) {
		boolean valid = true;
		contInsertions += 1;
		for (int i = 1; i < sentence.length() && valid == true; i++) {
			if (Character.isLowerCase(sentence.charAt(i))) {
				if (!existsProduction("T" + contInsertions,
						Character.toString(sentence.charAt(i)), g,
						newSetOfRules)) {
					Rule r = new Rule();
					r.setleftSide("T" + contInsertions);
					r.setrightSide(Character.toString(sentence.charAt(i)));
					newSetOfRules.add(r);
					contInsertions++;
				}
			} else
				valid = false;
		}
		return newSetOfRules;
	}

	private static boolean canInsert(String sentence) {
		String[] productions = sentence.split(" | ");
		boolean insert = false;
		int contNumbers = 0;
		String target = productions[productions.length - 1];
		for (int i = 0; i < target.length(); i++) {
			if (Character.isDigit(target.charAt(i)))
				contNumbers++;
		}
		if ((contNumbers == 0 && target.length() != 2)
				|| (contNumbers == 1 && target.length() != 3)
				|| (contNumbers == 2 && target.length() != 4))
			insert = true;
		return insert;
	}

	public static Grammar FNC(Grammar g) {

		Set<Rule> newSetOfRules = new HashSet<>();
		int contInsertions = 1;

		for (Rule element : g.getRule()) {
			String newProduction = new String();
			String[] aux = element.getrightSide().split(" | ");
			for (int i = 0; i < aux.length; i++) {
				aux[i] = aux[i].trim();
				String sentence = aux[i];
				int cont = 0;
				int insertionsOnNewProduction = 0;
				while (sentence.length() > 2) {
					if (Character.isLowerCase(sentence.charAt(0))) {
						if (cont == 0) {
							if (existsProduction(element.getleftSide(),
									Character.toString(sentence.charAt(cont)),
									g, newSetOfRules)) {
								// há uma produção deste tipo, não é necessário
								// realizar
								// inserções
								if (canInsert(newProduction)) {
									newProduction = getVariable(
											Character.toString(sentence
													.charAt(cont)), g,
											newSetOfRules);
									insertionsOnNewProduction++;
								}
								// sentence =
								// getVariable(Character.toString(sentence.charAt(0)),
								// g, newSetOfRules) + sentence.substring(1);
								sentence = sentence.substring(1);
							} else {
								// não há produções deste tipo, então uma
								// inserção é feita
								Rule r = new Rule();
								r.setleftSide("T" + contInsertions);
								r.setrightSide(Character.toString(sentence
										.charAt(cont)));
								newSetOfRules.add(r);
								if (canInsert(newProduction)) {
									newProduction = "T" + contInsertions;
									insertionsOnNewProduction++;
								}
								sentence = sentence.substring(1);
								// contInsertions++;
								contInsertions = updateNumberOfInsertions(
										newSetOfRules, contInsertions);
							}
							cont++;
						} else {
							// é minúsculo e está na segunda posição
							if (canInsert(newProduction)) {
								newProduction += "T" + contInsertions;
								insertionsOnNewProduction++;
							}

							if (existsProduction(element.getleftSide(),
									Character.toString(sentence.charAt(0)), g,
									newSetOfRules)) {
								Rule rule = new Rule();
								rule.setleftSide("T" + contInsertions);
								rule.setrightSide(insertRightRide(
										getVariable(Character.toString(sentence
												.charAt(0)), g, newSetOfRules)
												+ sentence, contInsertions,
										newSetOfRules, g));
								newSetOfRules.add(rule);
								sentence = sentence.substring(1);
								contInsertions++;
							} else {
								Rule rule = new Rule();
								rule.setleftSide("T" + (contInsertions + 1));
								rule.setrightSide(Character.toString(sentence
										.charAt(0)));
								newSetOfRules.add(rule);
								sentence = "T" + (contInsertions + 1)
										+ sentence.substring(1);
								Rule r = new Rule();
								r.setleftSide("T" + contInsertions);
								r.setrightSide(insertRightRide(sentence,
										contInsertions + 1, newSetOfRules, g));
								newSetOfRules.add(r);
								if (Character.isDigit(sentence.charAt(1))) {
									sentence = sentence.substring(2);
								} else {
									sentence = sentence.substring(1);
								}
								// cont = 0;
								// contInsertions += 2;
								contInsertions = updateNumberOfInsertions(
										newSetOfRules, contInsertions);
							}
						}
					} else if (!Character.isLowerCase(sentence.charAt(0))) {
						// maiúsculo na primeira posição
						if (cont == 0) {
							cont++;
							boolean control = false;
							if (canInsert(newProduction)) {
								newProduction += sentence.charAt(0);
								control = true;
							}
							if (Character.isDigit(sentence.charAt(1))) {
								if (control)
									newProduction += sentence.charAt(1);
								sentence = sentence.substring(2);
							} else {
								sentence = sentence.substring(1);
							}
						} else {
							// maiúsculo na segunda posição
							// verifica se produção já existe
							if (existsProduction(element.getleftSide(),
									sentence, g, newSetOfRules)) {
								if (canInsert(newProduction)) {
									newProduction += getVariable(sentence, g,
											newSetOfRules);
									insertionsOnNewProduction++;
								}
								Rule r = new Rule();
								r.setleftSide("T" + contInsertions);
								r.setrightSide(insertRightRide(sentence,
										contInsertions, newSetOfRules, g));
								newSetOfRules.add(r);
								// sentence = newProduction +=
								// getVariable(sentence, g);
								if (sentence.length() == 2)
									sentence += sentence.substring(2);
								else
									sentence += sentence.substring(1);
								// cont = 0;
								// contInsertions++;
								contInsertions = updateNumberOfInsertions(
										newSetOfRules, contInsertions);
							} else {
								// se produção não existe
								boolean control = false;
								if (canInsert(newProduction)) {
									newProduction += "T" + contInsertions;
									insertionsOnNewProduction++;
								}
								Rule r = new Rule();
								r.setleftSide("T" + contInsertions);
								r.setrightSide(insertRightRide(sentence,
										contInsertions, newSetOfRules, g));
								newSetOfRules.add(r);
								// contInsertions++;
								contInsertions = updateNumberOfInsertions(
										newSetOfRules, contInsertions);
								// cont = 0;
								if (Character.isDigit(sentence.charAt(1))) {
									if (control)
										newProduction += sentence.charAt(1);
									sentence = sentence.substring(2);
								} else {
									sentence = sentence.substring(1);
								}
							}
						}
					}
				}
				if (sentence.length() == 2) {
					if (Character.isUpperCase(sentence.charAt(0))
							&& Character.isUpperCase(sentence.charAt(1))) {
						if (!existsProduction(element.getleftSide(), sentence,
								g, newSetOfRules)) {
							if (canInsert(newProduction)) {
								if (newProductionSize(newProduction) == 1) {
									Rule auxRule = new Rule();
									auxRule.setleftSide("T" + contInsertions);
									auxRule.setrightSide(sentence);
									newSetOfRules.add(auxRule);
									newProduction += "T" + contInsertions;
									contInsertions = updateNumberOfInsertions(
											newSetOfRules, contInsertions);
								} else {
									newProduction += sentence;
								}
							} else {
								Rule lastRule = new Rule();
								lastRule.setleftSide("T" + contInsertions);
								lastRule.setrightSide(sentence);
								newSetOfRules.add(lastRule);
								contInsertions = updateNumberOfInsertions(
										newSetOfRules, contInsertions);
							}
						}
					} else if (Character.isUpperCase(sentence.charAt(0))
							&& Character.isLowerCase(sentence.charAt(1))) {
						String secondChar = sentence.substring(sentence
								.length() - 1);
						if (!existsProduction(element.getleftSide(),
								secondChar, g, newSetOfRules)) {
							sentence = sentence.substring(0,
									sentence.length() - 1)
									+ "T"
									+ contInsertions;
							Rule lastRule = new Rule();
							lastRule.setleftSide("T" + contInsertions);
							lastRule.setrightSide(secondChar);
							newSetOfRules.add(lastRule);
						} else {
							sentence = sentence.substring(0,
									sentence.length() - 1)
									+ getVariable(secondChar, g, newSetOfRules);
						}
						if (canInsert(newProduction)) {
							if (newProductionSize(newProduction) == 1) {
								newProduction += "T" + contInsertions;
							} else {
								newProduction += sentence;
							}
						} else {
							Rule auxRule = new Rule();
							auxRule.setleftSide("T" + contInsertions);
							auxRule.setrightSide(sentence);
							newSetOfRules.add(auxRule);
						}
						contInsertions = updateNumberOfInsertions(
								newSetOfRules, contInsertions);
					} else if (Character.isLowerCase(sentence.charAt(0))
							&& Character.isUpperCase(sentence.charAt(1))) {
						if (!existsProduction(element.getleftSide(),
								Character.toString(sentence.charAt(0)), g,
								newSetOfRules)) {
							Rule auxRule = new Rule();
							auxRule.setleftSide("T" + (contInsertions + 1));
							auxRule.setrightSide(Character.toString(sentence
									.charAt(0)));
							newSetOfRules.add(auxRule);
							sentence = "T" + (contInsertions + 1)
									+ sentence.substring(1);
							// contInsertions++;
							// contInsertions -= 1;
						} else {
							sentence = getVariable(
									Character.toString(sentence.charAt(0)), g,
									newSetOfRules) + sentence.substring(1);
						}
						if (canInsert(newProduction)) {
							if (newProductionSize(newProduction) == 1) {
								Rule auxRule = new Rule();
								auxRule.setleftSide("T" + contInsertions);
								auxRule.setrightSide(sentence);
								newSetOfRules.add(auxRule);
								newProduction += "T" + contInsertions;
								contInsertions = updateNumberOfInsertions(
										newSetOfRules, contInsertions);
							} else {
								newProduction += sentence;
							}
						} else {
							Rule auxRule = new Rule();
							auxRule.setleftSide("T" + contInsertions);
							auxRule.setrightSide(sentence);
							newSetOfRules.add(auxRule);
							contInsertions = updateNumberOfInsertions(
									newSetOfRules, contInsertions);
						}
					} else if (Character.isLowerCase(sentence.charAt(0))
							&& Character.isLowerCase(sentence.charAt(1))) {
						String secondChar;
						if (!existsProduction(element.getleftSide(),
								Character.toString(sentence.charAt(0)), g,
								newSetOfRules)) {
							Rule auxRule = new Rule();
							auxRule.setleftSide("T" + contInsertions);
							auxRule.setrightSide(Character.toString(sentence
									.charAt(0)));
							newSetOfRules.add(auxRule);
							sentence = "T" + (contInsertions)
									+ sentence.substring(1);
							// contInsertions++;
							contInsertions = updateNumberOfInsertions(
									newSetOfRules, contInsertions);
							secondChar = Character.toString(sentence.charAt(2));
						} else {
							// if (canInsert(newProduction))
							// newProduction += "T" + contInsertions;
							sentence = getVariable(
									Character.toString(sentence.charAt(0)), g,
									newSetOfRules) + sentence.substring(1);
							secondChar = assignsChar(sentence);
						}
						if (!existsProduction(element.getleftSide(),
								secondChar, g, newSetOfRules)) {
							Rule auxRule = new Rule();
							auxRule.setleftSide("T" + contInsertions);
							auxRule.setrightSide(secondChar);
							newSetOfRules.add(auxRule);
							sentence = sentence.substring(0,
									sentence.length() - 1)
									+ "T"
									+ contInsertions;
							contInsertions = updateNumberOfInsertions(
									newSetOfRules, contInsertions);
							// Rule lastRule = new Rule();
							// lastRule.setleftSide("T" + contInsertions);
							// lastRule.setrightSide(sentence);
							// newSetOfRules.add(lastRule);
							// contInsertions++;
						} else {
							sentence = sentence.substring(0,
									sentence.length() - 1)
									+ getVariable(secondChar, g, newSetOfRules);
							// Rule auxRule = new Rule();
							// auxRule.setleftSide("T" + contInsertions);
							// auxRule.setrightSide(sentence);
							// newSetOfRules.add(auxRule);
							// contInsertions++;
						}
						if (canInsert(newProduction)) {
							if (newProductionSize(newProduction) == 1) {
								Rule auxRule = new Rule();
								auxRule.setleftSide("T" + contInsertions);
								auxRule.setrightSide(sentence);
								newSetOfRules.add(auxRule);
								newProduction += "T" + contInsertions;
								contInsertions = updateNumberOfInsertions(
										newSetOfRules, contInsertions);
							} else
								newProduction += sentence;
						} else {
							Rule auxRule = new Rule();
							auxRule.setleftSide("T" + contInsertions);
							auxRule.setrightSide(sentence);
							newSetOfRules.add(auxRule);
							contInsertions = updateNumberOfInsertions(
									newSetOfRules, contInsertions);
						}
					}
				} else if (sentence.length() == 1) {
					if (canInsert(newProduction))
						newProduction += sentence;
				}
				newProduction += " | ";
			}
			if (newProduction.charAt(newProduction.length() - 2) == '|') {
				newProduction = newProduction.substring(0,
						newProduction.length() - 2);
				newProduction = newProduction.trim();
			}
			Rule r = new Rule();
			r.setleftSide(element.getleftSide());
			r.setrightSide(newProduction);
			newSetOfRules.add(r);
		}
		g.setRule(newSetOfRules);
		return g;
	}

	private static int newProductionSize(String newProduction) {
		int count = 0;
		String[] aux = newProduction.split(" | ");
		for (int i = 0; i < aux[aux.length - 1].length(); i++) {
			if (Character.isLetter(aux[aux.length-1].charAt(i)))
				count++;
		}
		return count;
	}

	private static String assignsChar(String sentence) {
		String result;
		if (Character.isDigit(sentence.charAt(1)))
			result = sentence.substring(2);
		else
			result = sentence.substring(1);
		return result;
	}
}