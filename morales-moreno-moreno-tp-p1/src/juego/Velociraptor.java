package juego;

import java.awt.Image;
import java.awt.Point;
import entorno.Entorno;
import entorno.Herramientas;

public class Velociraptor {

	private double x;
	private double y;
	private double velocidadVertical;
	private Image img;
	private double gravedad;
	private double factorDeDesplazamiento;
	private int ancho;
	private int alto;

	public Velociraptor(Entorno e, EstructuraDePisos estructura) {
		this.img = Herramientas.cargarImagen("velociraptor.png");
		this.x = 100;
		this.y = estructura.alturaDelPiso(4) - 32;
		this.factorDeDesplazamiento = 4;
		this.velocidadVertical = 0;
		this.gravedad = 0;
		this.alto = img.getHeight(e);
		this.ancho = img.getWidth(e);
	}

	public void dibujar(Entorno e, EstructuraDePisos estructura) {
		if (estructura.numeroDePiso(this.y) % 2 == 0) {
			img = Herramientas.cargarImagen("velociraptor.png");
		} else {
			img = Herramientas.cargarImagen("velociraptor2.png");
		}

		e.dibujarImagen(img, x, y, 0, 0.15);
	}

	public void mover(Entorno e, EstructuraDePisos estructura) {
		if (estructura.hayBloques(x, y + 32)) {
			gravedad = 0;
			velocidadVertical = 0;
			if (estructura.numeroDePiso(this.y) % 2 == 0 && x < e.ancho() - 50) {
				x += factorDeDesplazamiento;
			}
			if (estructura.numeroDePiso(this.y) % 2 != 0) {
				x += -factorDeDesplazamiento;
			}
		} else {
			int piso = estructura.numeroDePiso(y + 32);
			if (y < estructura.alturaDelPiso(piso - 1)) {
				gravedad = 1;
				velocidadVertical += gravedad;
				y += velocidadVertical;
			}
		}
	}

	public boolean llegoAlLimiteDel(Entorno e) {
		return x < 10;
	}

	public boolean superasteLaDistanciaDelPuntoDeAparicion(Entorno e) {
		return x > e.ancho() / 2 + 200;
	}

	public RayoLaser dispararRayoLaser(Entorno e, EstructuraDePisos estructura) {
		if (estructura.numeroDePiso(this.y) % 2 == 0) {
			return new RayoLaser(this.x, this.y, true);
		} else {
			return new RayoLaser(this.x - 200, this.y, false);
		}
	}

	// prueba
	public boolean chocasteConRelampago(EstructuraDePisos estructura, Relampago r) {
		return estructura.numeroDePiso(r.getY()) == estructura.numeroDePiso(y) && x < r.getX() + r.getAncho() / 2
				&& x > r.getX() - r.getAncho() / 2;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}
