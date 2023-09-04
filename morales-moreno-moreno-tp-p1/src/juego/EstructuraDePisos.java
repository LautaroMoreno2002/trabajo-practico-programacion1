package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class EstructuraDePisos {

	private int cantidadDePisos;
	private Image imgPiso;
	private int[][] bloquesDelPiso;
	private int anchoDelBloque;
	private int altoDelBloque;

	public EstructuraDePisos(Entorno e, int numeroDePisos) {
		this.cantidadDePisos = numeroDePisos;
		this.imgPiso = Herramientas.cargarImagen("ladrillo.png");
		this.anchoDelBloque = imgPiso.getWidth(e); // vale 32
		this.altoDelBloque = imgPiso.getHeight(e);
		this.bloquesDelPiso = new int[cantidadDePisos][e.ancho() / anchoDelBloque];
		// Crea el primer piso:
		for (int j = 0; j < e.ancho() / anchoDelBloque; j++) {
			bloquesDelPiso[0][j] = 1;
		}
		// Crea el resto de los pisos
		for (int i = 1; i < cantidadDePisos; i++) {
			for (int j = 0; j < e.ancho() / anchoDelBloque; j++) {
				bloquesDelPiso[i][j] = 1;
				if (i % 2 == 1 && j > e.ancho() / anchoDelBloque - 3) {
					bloquesDelPiso[i][j] = 0;
				}
				if (i % 2 == 0 && j < 2) {
					bloquesDelPiso[i][j] = 0;
				}
			}
		}
	}

	public void dibujar(Entorno e) {
		double x;
		double y;
		for (int i = 0; i < cantidadDePisos; i++) {
			for (int j = 0; j < e.ancho() / anchoDelBloque; j++) {
				x = j * anchoDelBloque + anchoDelBloque / 2;
				y = alturaDelPiso(i + 1) + altoDelBloque / 2;
				if (bloquesDelPiso[i][j] == 1) {
					e.dibujarImagen(imgPiso, x, y, 0, 1);
				}
			}
		}
	}

	public boolean hayBloques(double xDeUnPersonaje, double yDeUnPersonaje) {
		for (int i = 0; i < cantidadDePisos; i++) {
			if (yDeUnPersonaje >= alturaDelPiso(i + 1) && yDeUnPersonaje < alturaDelPiso(i + 1) + altoDelBloque) {
				for (int j = 0; j < bloquesDelPiso[i].length; j++) {
					if (j * anchoDelBloque <= xDeUnPersonaje && xDeUnPersonaje < (j + 1) * anchoDelBloque) {
						return bloquesDelPiso[i][j] == 1;
					}
				}
			}
		}
		return false;
	}

	public int numeroDePiso(double yDeUnPersonaje) {
		for (int i = cantidadDePisos; i > -3; i--) {
			if (yDeUnPersonaje <= alturaDelPiso(i + 1) && yDeUnPersonaje > alturaDelPiso(i + 2)) {
				return (i + 1);
			}
		}
		return -1;
	}

	public double alturaDelPiso(int piso) {
		return 500 - (500 * (piso - 1) / cantidadDePisos); // expliquen en el informe
	}
}