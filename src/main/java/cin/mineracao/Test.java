package cin.mineracao;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import org.apache.lucene.queryparser.classic.ParseException;


public class Test {

	public static void main(String[] args) throws ParseException, IOException {
		int[] matrixConsulta1 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,1,0,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,1,0,0,1,1,1,1,0,1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,0,1,1,1,1,0,1,0,1};
		int[] matrixConsulta2 = {1,0,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,1,0,1,1,0,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[] matrixConsulta3 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,1,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

		String query1 = "health AND (food OR supplement)";
		String query2 = "acquiring vocabulary in L2";
		String query3 = "\"the dark matter\"";

		int[] r;
		String[] s;
		CustomAnalyzer analyzer = null;
		
		int relevantes;
		int retornados;
		int relevantesRetornados;
		double precisao, cobertura, fmeasure = 0;
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Matrizes de Avaliação.csv", false)));
		writer.write("Consulta/Documento");
		for (int i = 1; i <= 300; i++) {
			writer.write(", " + i);
		}
		writer.newLine();

		for (int i = 1; i <= 4; i++) {
			switch (i) {
			case 1:
				analyzer = new CustomAnalyzer(false, false);
				break;

			case 2:
				analyzer = new CustomAnalyzer(true, false);
				break;
				
			case 3:
				analyzer = new CustomAnalyzer(false, true);
				break;
				
			case 4:
				analyzer = new CustomAnalyzer(true, true);
				break;
				
			default:
				break;
			}

			System.out.println("-----------------Base " + i + "-----------------------");
			
			System.out.println("Consulta 1");
			
			writer.write(query1);
			

			
			r = Engine.searchTest(query1, analyzer);
			relevantes = sumArray(matrixConsulta1);
			retornados = sumArray(r);
			relevantesRetornados = relevantesRetornados(r, matrixConsulta1);
			precisao = (double) relevantesRetornados / retornados;
			cobertura = (double) relevantesRetornados / relevantes;
			fmeasure = (2 * precisao * cobertura) / (precisao + cobertura);
			
			System.out.println(Arrays.toString(matrixConsulta1));
			System.out.println(Arrays.toString(r));
			System.out.println("Retornados: " + retornados);
			System.out.println("Relevantes retornados: " + relevantesRetornados);
			System.out.println("Relevantes: " + relevantes);
			System.out.println("Precisao: " + precisao);
			System.out.println("Cobertura: " + cobertura);
			System.out.println("F-Measure: " + fmeasure);
			System.out.println();

			s = compare(r, matrixConsulta1);

			System.out.println(Arrays.toString(s));
			System.out.println("Acertos: " + getCertos(s));
			System.out.println("Erros: " + getErrados(s));
			System.out.println();
			
			for(int j = 0; j < s.length; j++) {
				writer.write(", " + s[j]);
			}
			
			writer.newLine();

			//------------------------------------------------------------------

			System.out.println("Consulta 2");
			
			writer.write(query2);
			
			
			
			r = Engine.searchTest(query2, analyzer);
			relevantes = sumArray(matrixConsulta2);
			retornados = sumArray(r);
			relevantesRetornados = relevantesRetornados(r, matrixConsulta2);
			precisao = (double) relevantesRetornados / retornados;
			cobertura = (double) relevantesRetornados / relevantes;
			
			System.out.println(Arrays.toString(matrixConsulta2));
			System.out.println(Arrays.toString(r));
			System.out.println("Retornados: " + retornados);
			System.out.println("Relevantes retornados: " + relevantesRetornados);
			System.out.println("Relevantes: " + relevantes);
			System.out.println("Precisao: " + precisao);
			System.out.println("Cobertura: " + cobertura);
			System.out.println("F-Measure: " + fmeasure);
			System.out.println();

			s = compare(r, matrixConsulta2);

			System.out.println(Arrays.toString(s));
			System.out.println("Acertos: " + getCertos(s));
			System.out.println("Erros: " + getErrados(s));
			System.out.println();
			
			for(int j = 0; j < s.length; j++) {
				writer.write(", " + s[j]);
			}
			
			writer.newLine();

			//------------------------------------------------------------------

			System.out.println("Consulta 3");
			
			writer.write(query3);
			
			
			r = Engine.searchTest(query3, analyzer);
			relevantes = sumArray(matrixConsulta3);
			retornados = sumArray(r);
			relevantesRetornados = relevantesRetornados(r, matrixConsulta3);
			precisao = (double) relevantesRetornados / retornados;
			cobertura = (double) relevantesRetornados / relevantes;
			
			System.out.println(Arrays.toString(matrixConsulta3));
			System.out.println(Arrays.toString(r));
			System.out.println("Retornados: " + retornados);
			System.out.println("Relevantes retornados: " + relevantesRetornados);
			System.out.println("Relevantes: " + relevantes);
			System.out.println("Precisao: " + precisao);
			System.out.println("Cobertura: " + cobertura);
			System.out.println("F-Measure: " + fmeasure);
			System.out.println();

			s = compare(r, matrixConsulta3);

			System.out.println(Arrays.toString(s));
			System.out.println("Acertos: " + getCertos(s));
			System.out.println("Erros: " + getErrados(s));
			System.out.println();
			
			for(int j = 0; j < s.length; j++) {
				writer.write(", " + s[j]);
			}
			
			writer.newLine();
			
			writer.newLine();
		}
		
		writer.close();

	}



	private static String[] compare(int[] a, int[] b) {
		String[] s = new String[300];
		for (int i = 0; i < 300; i++) {
			if (a[i] == b[i]) {
				s[i] = "certo";

			} else {
				s[i] = "errado";
			}
		}
		return s;
	}

	private static int getCertos(String[] s) {
		int r = 0;
		for (String a : s) {
			if (a.compareTo("certo") == 0) {
				r++;
			}


		}
		return r;
	}

	private static int getErrados(String[] s) {
		int r = 0;
		for (String a : s) {
			if (a.compareTo("errado") == 0) {
				r++;
			}

		}
		return r;
	}
	
	private static int sumArray(int[] array) {
		int r = 0;
		for (int i = 0; i < array.length; i++) {
			r += array[i];
		}
		
		return r;
	}
	
	private static int relevantesRetornados(int[] a, int[] b) {
		int r = 0;
		for (int i = 0; i < 300; i++) {
			if (a[i] == 1 && b[i]== 1) {
				r++;

			}
		}
		return r;

	}


}
