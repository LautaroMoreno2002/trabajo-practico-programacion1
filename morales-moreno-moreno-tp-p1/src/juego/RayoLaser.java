package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class RayoLaser {
	
	private double x;
	private double y;
	private int velocidad;
	private double tamanio;
	private Image img;

	public RayoLaser(double x, double y, boolean direccion) {
		this.x = x + 100;
		this.y = y - 10;
		this.tamanio = 0.6;
		this.img = Herramientas.cargarImagen("Rayo.png");
		this.velocidad = direccion ? 10 : -10; 
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

}
