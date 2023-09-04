package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Relampago {

	private double x;
	private double y;
	private double tamanio;
	private Image img;
	private int velocidad;
	private int ancho;
	private int alto;

	public Relampago(double x, double y, boolean direccion, Entorno e) {
		this.x = x + 40;
		this.y = y - 10;
		this.tamanio = 0.5;
		this.img = Herramientas.cargarImagen("Rayo.png");
		this.velocidad = direccion ? 12 : -12;
		this.alto = img.getHeight(e);
		this.ancho = img.getWidth(e);
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0, tamanio);
	}

	public void mover(Entorno e, EstructuraDePisos estructura) {
		x += velocidad;
	}

	public boolean llegoAlBordeDeLaPantalla(Entorno e) {
		return x > e.ancho() || x < 0;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAlto() {
		return this.alto;
	}

	public int getAncho() {
		return this.ancho;
	}
}