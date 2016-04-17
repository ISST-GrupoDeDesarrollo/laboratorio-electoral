package es.upm.dit.isst;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.upm.dit.isst.dao.UserDAOImpl;

public class GetVoteResults extends HttpServlet{
	/*
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(404);
	}
	
	
	//Se recibe el objeto JSON con los par�metros de la simulaci�n
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		//Se extrae la informaci�n de la petici�n
		int numeroCircunscripciones = req.getParameter();
		int numeroPartidos = req.getParameter();
		int [][]votosPartidoPorCircuns = req.getParemeter();
		int []numeroEscaPorCircuns = req.getParameter();
		String metodoAsigna = req.getParameter(); //------ Cambiar string de identificacion abajo
		int []partidos = req.getParameter();
		int [][]resultados = new int[numeroCircunscripciones][numeroPartidos];
		int []poblacionEncuestadaPorCircuns = req.getParameter();
		int []poblacionTotalPorCircuns = req.getParameter();
		
		
		
		//Se realizan los c�lculos seg�n el m�todo de asignaci�n de esca�os
		for(int k=0;k<numeroCircunscripciones;k++){
			numeroEscaPorCircuns = asignacion(partidos, numeroEscaPorCircuns[k], 
					votosPartidoPorCircuns[k], metodoAsigna);
			resultados[k] = numeroEscaPorCircuns;
		}
		
		//Se guardan los resultados en base de datos
		UserDAOImpl dao = UserDAOImpl.getInstance();
		dao.guardarParametros(parametros);
		
		
		//Se env�an los resultados
		session.setAttribute("resultados", resultados);
		session.setAttribute("desviacion", desviacion);
		resp.setStatus(200);
	}
	
	
	
	//M�todo D'HONT/Sainte-Lagu� 
	// se llama a este metodo una vez por circunscripci�n
	// []partidos-> partidos a los que se asignan los esca�os
	// nEsca-> numero de esca�os asignados a esta circunscripcion
	// votosPartido-> numero votos de cada partido
	// 
	public static int[] asignacion(int [] partidos, int nEsca, int [] votosPartido, String metodoAsigna){
		int [] cociente = new int[partidos.length];
		int [] escPartido = new int[partidos.length];
		
		for(int i=0;i<partidos.length;i++){
			cociente[i] = 1;
			escPartido[i] = 0;
		}
		
		for(int i=0;i<nEsca;i++){
			int posicionGana = 0;
			for(int j=1;j<partidos.length;j++){	
				if( votosPartido[j]/cociente[j] > votosPartido[posicionGana]/cociente[posicionGana]){
					posicionGana = j;
				}
			}
			escPartido[posicionGana]++;
			if(metodoAsigna == "DHondt"){
				cociente[posicionGana]++;
			}else{
				cociente[posicionGana] = cociente[posicionGana] + 2;
			}
		}
		
		return escPartido;
	}
	
	
	public static int calcularDesviacionExtrapolacion(int probabilidad, int poblacionTotal, int poblacionEncues){
		int desviacion = (poblacionTotal - poblacionEncues)/poblacionEncues * (1-probabilidad);
		return desviacion;
	}
	
	*/
}
