package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Image fondo;
	private EstructuraDePisos estructuraDePisos;
	private Barbarianna barbarianna;
	private Commodore commodore;
	private Velociraptor[] velociraptors;
	private Relampago relampago;
	private RayoLaser rayoLaser;
	private int puntaje;
	private int muertes;
	private int velociraptorsEnPantalla;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Barbarianna Viking Edition - Grupo 1 - v0.1", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo.png");
		this.estructuraDePisos = new EstructuraDePisos(entorno, 4);
		this.barbarianna = new Barbarianna(entorno, estructuraDePisos);
		this.commodore = new Commodore(entorno);
		this.velociraptors = new Velociraptor[6];
		this.puntaje = 0;
		this.muertes = 0;
		this.velociraptorsEnPantalla = 0;

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
		estructuraDePisos.dibujar(entorno);
		commodore.dibujar(entorno);

		// Cuando Barbarianna llegue a la Commodore
		if (barbarianna != null && barbarianna.llegasteAlCommodore(estructuraDePisos, commodore)) {
			entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, 800, 600, 0, Color.BLACK);
			entorno.cambiarFont("impact", 30, Color.WHITE);
			entorno.escribirTexto("Felicidades.  ¡¡¡Venciste al Doctor Gero !!!", entorno.ancho() / 2 - 200,
					entorno.alto() / 2 - 50);
			entorno.cambiarFont("impact", 20, Color.WHITE);
			entorno.escribirTexto("Puntaje: " + puntaje, entorno.ancho() / 2 - 100, entorno.alto() / 2 + 40);
			entorno.escribirTexto("Total de muertes: " + muertes, entorno.ancho() / 2 - 100, entorno.alto() / 2 + 120);
			return;
		}
		if (barbarianna == null) {
			entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, 800, 600, 0, Color.BLACK);
			entorno.cambiarFont("impact", 30, Color.WHITE);
			entorno.escribirTexto("Perdiste. ���suerte la proxima!!!", entorno.ancho() / 2 - 200,
					entorno.alto() / 2 - 50);
			return;
		}
		velociraptorsEnPantalla = 0;
		
		// Dibuja los velociraptors
		for (Velociraptor v : velociraptors) {
			if (v != null) {
				v.mover(entorno, estructuraDePisos);
				v.dibujar(entorno, estructuraDePisos);
				velociraptorsEnPantalla++;
			}
		}
		
		Random rand = new Random();
		if (rand.nextInt(100)/2==0 && rayoLaser==null && velociraptors[0]!=null) {
			rayoLaser = velociraptors[0].dispararRayoLaser(entorno, estructuraDePisos);
		}

		//rayoLaser
		if (rayoLaser != null) {
			rayoLaser.mover(entorno, estructuraDePisos);
			rayoLaser.dibujar(entorno);
			if (barbarianna !=null && barbarianna.chocasteConUnRayoLaser(estructuraDePisos, rayoLaser) && !barbarianna.getEstaAgachada()) {
				barbarianna =null;
				rayoLaser = null;
			}
			else {
				if (rayoLaser.llegoAlBordeDeLaPantalla(entorno)) {
					rayoLaser=null;
			}
			}
		}
		// inicializa los velocirraptores

		if (velociraptorsEnPantalla < 6) {
			if (velociraptorsEnPantalla == 0) {
				velociraptors[velociraptorsEnPantalla] = new Velociraptor(entorno, estructuraDePisos);
			}
		}
		if (velociraptorsEnPantalla > 0 && velociraptorsEnPantalla < 6
				&& velociraptors[velociraptorsEnPantalla - 1].superasteLaDistanciaDelPuntoDeAparicion(entorno)) {
			velociraptors[velociraptorsEnPantalla] = new Velociraptor(entorno, estructuraDePisos);
		}

		// dibuja frame de estado del juego:
		entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() - 35, entorno.ancho(), 70, 0, Color.BLACK);
		entorno.dibujarRectangulo(entorno.ancho() / 2 - 5, entorno.alto() - 45, 150, 30, 0, Color.WHITE);
		entorno.dibujarRectangulo(entorno.ancho() - 60, entorno.alto() - 45, 90, 30, 0, Color.WHITE);

		// Muestro en pantalla los puntos, las vidas y las muertes.
		entorno.cambiarFont("impact", 16, Color.RED);
		entorno.escribirTexto("Puntos: " + puntaje, entorno.ancho() / 2 - 30, entorno.alto() - 35);
		entorno.escribirTexto("Muertes: " + muertes, entorno.ancho() - 100, entorno.alto() - 35);

		// Barbarianna
		if (barbarianna != null) {
			barbarianna.mover(entorno, estructuraDePisos);
			barbarianna.dibujar(entorno);
			if (entorno.estaPresionada('w')) {
				barbarianna.saltarEnElPiso(estructuraDePisos);
			}

			if (entorno.estaPresionada('a')) {
				barbarianna.girarIzquierda(estructuraDePisos);
			}

			if (entorno.estaPresionada('s')) {
				barbarianna.agacharse(entorno);
			}

			if (!entorno.estaPresionada('s')) {
				barbarianna.pararse(entorno);
			}

			if (entorno.estaPresionada('d')) {
				barbarianna.girarDerecha(entorno, estructuraDePisos);
			}

			if (entorno.estaPresionada('u')) {
				barbarianna.subirAlPisoSuiguiente(estructuraDePisos, entorno);
			}

			if (entorno.estaPresionada(entorno.TECLA_ESPACIO) && relampago == null) {
				relampago = barbarianna.dispararRayo(entorno, estructuraDePisos);
			}

			if (velociraptors[0] != null
					&& barbarianna.chocasteConUnVelociraptor(estructuraDePisos, velociraptors[0])) {
				barbarianna = null;
			}
		}

		// Relampago
		if (relampago != null) {
			relampago.mover(entorno, estructuraDePisos);
			relampago.dibujar(entorno);
			if (velociraptors[0] != null && velociraptors[0].chocasteConRelampago(estructuraDePisos, relampago)) {
				relampago = null;
				eliminarPrimero(velociraptors);
				muertes++;
				puntaje = puntaje + 50;
			} else {
				if (relampago.llegoAlBordeDeLaPantalla(entorno)) {
					relampago = null;
				}
			}

		}

		// eliminar primer velocirraptor
		if (velociraptors[0].llegoAlLimiteDel(entorno) && velociraptors[0] != null) {
			eliminarPrimero(velociraptors);
		}
	}

	static void eliminarPrimero(Velociraptor v[]) {
		for (int i = 0; i < v.length - 1; i++) {
			v[i] = v[i + 1];
		}
		v[v.length - 1] = null;
	}

	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
