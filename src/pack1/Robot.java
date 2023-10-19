package pack1;

import java.util.Random;

public class Robot extends Thread {
	private String nombre;
	private int distancia;
	private int posicion;
	private long tiempoFinalizacion;
	 private boolean lesionado;

	public Robot(String nombre, int distancia) {
		this.nombre = nombre;
		this.distancia = distancia;
		this.posicion = 0;
		this.tiempoFinalizacion = 0;
		this.lesionado = false;
	}

	@Override
	 public void run() {
        int distanciaRecorrida = 0;

        while (distanciaRecorrida < distancia) {
            // Genera un número aleatorio entre 1 y 100
            int probabilidadLesion = new Random().nextInt(100) + 1;

            // Si la probabilidad es 1 (1%), el robot se lesiona
            if (probabilidadLesion == 1) {
                lesionar();
                System.out.println(nombre + " ha sido lesionado y no puede continuar.");
                break; // Sale del bucle
            }

            // Avance aleatorio si no se ha lesionado
            distanciaRecorrida += (int)(Math.random() * 10 + 1);

            // Actualiza la posición
            this.posicion = distanciaRecorrida;

            System.out.println(nombre + " ha recorrido " + distanciaRecorrida + " metros.");
            sleep();
        }

        if (!lesionado) {
            // Si no se lesionó, actualiza el tiempo de finalización y muestra que llegó a la meta
            this.tiempoFinalizacion = System.currentTimeMillis();
            System.out.println(nombre + " ha llegado a la meta.");
        }
    }
	public String getNombre() {
		return nombre;
	}
	   public void lesionar() {
	        lesionado = true;
	    }

	public int getPosicion() {
		return posicion;
	}

	public long getTiempoFinalizacion() {
		return tiempoFinalizacion;
	}

	public void sleep() {
		try {
			Thread.sleep(1000); // Pausa el programa durante 1 segundo (1000 milisegundos)
		} catch (InterruptedException e) {
			// Manejo de excepciones en caso de interrupción
			e.printStackTrace();
		}
	}

	public boolean isLesionado() {
		
		return lesionado;
	}
}
