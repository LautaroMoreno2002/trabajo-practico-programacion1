package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Barbarianna {

	private double x;
	private double y;
	private double factorDeDesplazamiento;
	private double velocidadHorizontal;
	private double velocidadVertical;
	private double gravedad;
	private int altura;
	private int ancho;
	private boolean estaAgachada;
	private Image img;

	public Barbarianna(Entorno e, EstructuraDePisos estructura) {
		this.img = Herramientas.cargarImagen("barbarinna.png");
		this.altura = img.getHeight(e);
		this.ancho = img.getWidth(e);
		this.x = 15;
		this.y = estructura.alturaDelPiso(1) - altura / 2;
		this.factorDeDesplazamiento = 5;
		this.velocidadHorizontal = 0;
		this.velocidadVertical = 0;
		this.gravedad = 0;
		this.estaAgachada = false;
	}

	public void dibujar(Entorno e) {
		if (estaAgachada) {
			img = Herramientas.cargarImagen("barbariannaAgachada.png"); // estaAgachada
			y = y + altura / 2;
			altura = img.getHeight(e);
			y = y - altura / 2;
		} else {
			img = Herramientas.cargarImagen("barbarinna.png");
			altura = img.getHeight(e);
		}

		e.dibujarImagen(img, x, y, 0);
	}

	// no va interaccion con le usuarie
	public void girarDerecha(Entorno e, EstructuraDePisos estructura) {
		if (x <= e.ancho() - 21) {
			velocidadHorizontal = factorDeDesplazamiento;
		}
		if (!estructura.hayBloques(x, y + altura / 2)) {
			gravedad = 1;
		}
	}

	public void girarIzquierda(EstructuraDePisos estructura) {
		if (x >= 21) {
			velocidadHorizontal = -factorDeDesplazamiento;
		}
		if (!estructura.hayBloques(x, y + altura / 2)) {
			gravedad = 1;
		}
	}

	public void saltarEnElPiso(EstructuraDePisos estructura) {
		if (estructura.hayBloques(x - 20, y + altura / 2)) {
			velocidadVertical = -2 * factorDeDesplazamiento;
			gravedad = 1;
		}
	}

	public void agacharse(Entorno e) {
		estaAgachada = true;
	}

	public void pararse(Entorno e) {
		y = y + altura / 2;
		estaAgachada = false;
		y = y - altura / 2;

	}

	public void subirAlPisoSuiguiente(EstructuraDePisos estructura, Entorno e) {
		int estePiso = estructura.numeroDePiso(y + altura / 2);
		if (estructura.numeroDePiso(y) != 4 && !estructura.hayBloques(x, estructura.alturaDelPiso(estePiso + 1))) {
			y = estructura.alturaDelPiso(estePiso + 1) - altura;
			gravedad = 1;
			if ((estePiso + 1) % 2 == 0) {
				x = e.ancho() - 82;
			} else {
				x = 82;
			}
		}
	}

	public void mover(Entorno e, EstructuraDePisos estructura) {
		velocidadVertical += gravedad;
		y += velocidadVertical;
		x += velocidadHorizontal;
		velocidadHorizontal = 0;

		if (estructura.hayBloques(x, y + altura / 2)) {
			gravedad = 0;
			velocidadVertical = 0;

			// evita que barbarianna se quede travada en algún bloque
			int piso = estructura.numeroDePiso(y + altura / 2);
			if (estructura.alturaDelPiso(piso) > y + altura / 2) {
				y = estructura.alturaDelPiso(piso + 1) - altura / 2;
			}
		}
	}

	public Relampago dispararRayo(Entorno e, EstructuraDePisos estructura) {
		if (estructura.numeroDePiso(this.y) % 2 != 0) {
			return new Relampago(this.x, this.y, true, e);
		} else {
			return new Relampago(this.x - 80, this.y, false, e);
		}
	}

	public boolean llegasteAlCommodore(EstructuraDePisos estructura, Commodore c) {
		return estructura.numeroDePiso(y) == estructura.numeroDePiso(c.getY()) && x - c.getX() <= 10;
	}

	public boolean chocasteConUnVelociraptor(EstructuraDePisos estructura, Velociraptor v) {
		return estructura.numeroDePiso(y) == estructura.numeroDePiso(v.getY()) && x + ancho / 2 >= v.getX()
				&& x - ancho / 2 <= v.getX();
	}
	
	public boolean chocasteConUnRayoLaser(EstructuraDePisos estructura, RayoLaser r) {
		return estructura.numeroDePiso(y) == estructura.numeroDePiso(r.getY()) && x + ancho / 2 >= r.getX()
				&& x - ancho / 2 <= r.getX();
	}
	
	public boolean getEstaAgachada() {
		return estaAgachada;
	}
}
