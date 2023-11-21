# IA

  La IA en el trabajo ha ayudado a añadir mejoras al código con métodos de java que desconociamos, como por ejemplo "System.currentTimeMillis()" que sirve para contar el tiempo y en este caso se ha utilizado para llevar una cuenta del tiempo que le ha llevado al robot llegar a la meta. Este valor se guarda en una varibale llamada tiempoFinalización y posteriormente se imprime el valor de esta por consola. Gracias a la IA también hemos podido crear el método Sleep(), cuya función es pausar el proceso durante 1 segundo (1000 milisegundos). Una de las cosas más importantes en las que ha influenciado la IA es a la hora de introducir el método 'join()', que sirve para que se pausen todos los hilos hasta que se encuentren todos en el mismo estado (en este caso finalizado) para que funcione el podio.
  La facilidad que ofrece la IA a la hora de crear nuevas funcionalidades permite que una vez cubiertos los requisitos del programa sea rápido y secillo el ir ampliandolo lo que nos ha permitido incluir la posibilidad de que los robots se lesionen durante la carrera ayudando incluso en la generación de comentarios de código
  
  También ha ayudado a la hora de implementar todas estas mejoras en el código y así hacerlo mejor y de forma más simple de la que alomejor se nos podría haber ocurrido a nosotros, por lo que tenemos un código más limpio y conmpacto.
  Las dos clases, después de haber sido mejoradas por la IA han resultado de la siguiente manera:

 ### LA CLASE ROBOT
 #### Codigo Inicial
 ```
import java.util.Random;

public class Robot extends Thread {
    private String nombre;

    public Robot(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        int distanciaRecorrida = 0;
        Random random = new Random();

        while (distanciaRecorrida < 100) { // Define la distancia de la carrera
            distanciaRecorrida += random.nextInt(10) + 1; // Avance aleatorio

            System.out.println(nombre + " ha recorrido " + distanciaRecorrida + " metros.");
        }

        System.out.println(nombre + " ha llegado a la meta.");
    }
}


```


 #### Código Final
```

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
```

### LA CLASE STARTRACE

#### Código Inicial
```
public class Robot extends Thread {
    private String nombre;
    private int posicion;
    private long tiempoFinalizacion;

    public Robot(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        // Lógica de la carrera

        // Cuando el robot cruza la línea de meta, actualiza su posición y tiempo
        this.posicion = calcularPosicion(); // Reemplaza calcularPosicion() con tu lógica
        this.tiempoFinalizacion = System.currentTimeMillis();

        System.out.println(nombre + " ha llegado a la meta en la posición " + posicion);
    }

    public int getPosicion() {
        return posicion;
    }

    public long getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }
}
```
#### Código Final

```

public class StartRace {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Introduzca la cantidad de robots que participarán en la carrera: ");
        int numRobots = in.nextInt();
        System.out.println("En la carrera participarán la friolera de " + numRobots + " Robots, buena suerte a todos");
        System.out.println("¿Qué distancia tendrá la carrera?: ");
        int distancia = in.nextInt();
        Robot[] robots = new Robot[numRobots];
        int numeracion = 1;
        
        // Variable para rastrear si al menos un robot llegó a la meta sin lesionarse
        boolean alMenosUnRobotLlego = false;

        for (int i = 0; i < numRobots; i++) {
            robots[i] = new Robot("Robot " + numeracion++, distancia);
        }

        for (int i = 0; i < numRobots; i++) {
            robots[i].start();
        }
        for (Robot robot : robots) {
            try {
                robot.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!robot.isLesionado() && robot.getPosicion() >= distancia) {
                alMenosUnRobotLlego = true;
            }
        }

        if (alMenosUnRobotLlego) {
            // Filtra los robots lesionados y ordena la matriz de robots por tiempo de finalización
            Robot[] robotsNoLesionados = Arrays.stream(robots)
                    .filter(robot -> !robot.isLesionado())
                    .sorted(Comparator.comparingLong(Robot::getTiempoFinalizacion))
                    .toArray(Robot[]::new);

            // Imprime los tres primeros robots no lesionados en el podio
            System.out.println("Podio:");
            for (int i = 0; i < 3 && i < robotsNoLesionados.length; i++) {
                Robot robot = robotsNoLesionados[i];
                System.out.println((i + 1) + "º lugar: " + robot.getNombre() + " (Tiempo: " + robot.getTiempoFinalizacion() + " ms)");
            }
        } else {
            System.out.println("Todos los robots se han lesionado, mala suerte.");
        }
    }
}

```

# PENSAMIENTO CRÍTICO

### 1

  Cuando el programa es ejecutado varias veces el resultado que se imprime en consola cambia ya que a parte de que la distancia que van recorriendo cada robot va variando mediante el método 'Math.random()', también cambia por la variable lesionado, que añade una probabilidad de 1% de que el robot se lesione, por lo que cabe la posibilidad de que alguno no termine la carrera. 
  Al ejecutarse varios hilos con elementos random en ellos provocará que nunca se repitan exactamente los mismo resultados
  
### 2

  El primer problema que puede surgir cuando los robots no se sincronizan entre ellos es la funcionalidad del podio. SI este se realiza en el main, como los hilos se ejecutan de forma paralela, llega al podio antes de finalizar la carrera. Para solucionar esto hemos optado por un join que espera a que todos los hilos se encuentren finalizados para continuar con el principal y anunciar a los ganadores.

Otro Problema es que al ejecutarse el hilos diferentes existe la dificultad de que para establecer este podio no se pueden pasar datos entre ellos, por lo que hemos decidido guardar en el array los objetos robots con los tiempos que han tardado en realizar la carrera y ordenarlos por tiempo, excluyendo a los lesionados. Esto nos da a los 3 que han tardado menos.
### 3

  La programación concurrente ayuda en el desarrollo de aplicaciones multiplataforma por varias razones:

  - Mejora del rendimiento: Como las aplicaciones multiplataforma se ejecutan en una gran variedad de dispositivos con diferentes recursos y capacidades de procesamiento, la programación concurrida permite aprovechar al máximo los recursos disponibles, distribuyendoi las tareas en múltiples hilos.
  - Utilización eficiente del CPU:  Al distribuirse las tareas en varios hilos, los procesadores multi-core permite acelerar las aplicaciones, por lo que serían más rápidas y eficientes.
  - Paralelismo de tareas: La programación concurrente facilita la gestión de estas tareas en paralelo, lo que puede aumentar la eficiencia y reducir los tiempos de espera.

  Como conclusión podemos obenter que la programación cocurrente es una herramienta fundamental y útil en el desarrollo de aplicaciones multiplataforma, ya que permite gestionar de manera eficiente la complejidad de interactuar con diversas plataformas y garantiza un rendimiento óptimo en estas.
