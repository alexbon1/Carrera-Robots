package pack1;

import java.util.*;

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
