package juego;

import java.awt.Image;
import java.awt.Point;

import entorno.Entorno;
import entorno.Herramientas;

public class Commodore {

	private double x;
	private double y;
	private Image img;

	public Commodore(Entorno e) {
		this.x = e.ancho() / 2;
		this.y = 79;
		this.img = Herramientas.cargarImagen("maquina.png");
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0, 0.13); // con escala por el tamanio de la imagen
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

}
