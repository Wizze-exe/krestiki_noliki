import java.util.Random;
import java.util.Scanner;
public class Main {
	public static int SIZE;
	public static int DOTS_TO_WIN;
	public static final char DOT_EMPTY = '•';
	public static final char DOT_X = 'X';
	public static final char DOT_O = 'O';
	public static char[][] map;
	public static Scanner sc = new Scanner(System.in);
	public static Random rand = new Random();
	public static void main(String[] args) {
		System.out.println("Введите размер поля: ");
		SIZE = sc.nextInt();
		DOTS_TO_WIN = SIZE;
		initMap();
		printMap();
		while (true) {
			humanTurn();
			printMap();
			if (checkWin(DOT_X)) {
				System.out.println("Победил человек");
				break;
			}
			if (isMapFull()) {
				System.out.println("Ничья");
				break;
			}
			aiTurn();
			printMap();
			if (checkWin(DOT_O)) {
				System.out.println("Победил Искуственный Интеллект");
				break;
			}
			if (isMapFull()) {
				System.out.println("Ничья");
				break;
			}
		}
		System.out.println("Игра закончена");
	}
	public static boolean checkWin(char symb) {
		int winCounter = 0;
		boolean flag = false;
		
		//Строки
		for (int i = 0; i < SIZE; i++) {
			winCounter = 0;
			for (int j = 0; j < SIZE; j++){
				if (map[i][j] == symb){
					winCounter++;
					if (winCounter == DOTS_TO_WIN) flag = true;
				}
				else break;
			}
		}
		winCounter = 0;
		
		//Столбцы
		for (int i = 0; i < SIZE; i++) {
			winCounter = 0;
			for (int j = 0; j < SIZE; j++) {
				if (map[j][i] == symb){
					winCounter++;
					if (winCounter == DOTS_TO_WIN) flag = true;
				}
				else break;
			}
		}
		winCounter = 0;
		
		//Главная диагональ
		for (int i = 0; i < SIZE; i++){
			if (map[i][i] == symb) winCounter++;
			else break;
			if (winCounter == DOTS_TO_WIN) flag = true;
		}
		winCounter = 0;
		
		//Побочная диагональ
		for (int i = 0; i < SIZE; i++){
			if (map[i][SIZE - 1 - i] == symb) winCounter++;
			else break;
			if (winCounter == DOTS_TO_WIN) flag = true;
		}
		
		return flag;
	}
	public static boolean isMapFull() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (map[i][j] == DOT_EMPTY) return false;
			}
		}
		return true;
	}
	public static void aiTurn() {
		int x, y;
		
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				if (isCellValid(i, j)){
					map[j][i] = DOT_O;
					if (checkWin(DOT_O)){
						System.out.println("Компьютер походил в точку " + (i + 1) + " " + (j + 1));
						map[j][i] = DOT_O;
						return;
					}
					map[j][i] = DOT_EMPTY;
				}
			}
		}
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (isCellValid(i, j)) {
					map[j][i] = DOT_X; // Ставим символ "X" на свободную ячейку
					if (checkWin(DOT_X)) { // Проверяем, может ли игрок выиграть на следующем ходу
						System.out.println("Компьютер походил в точку " + (i + 1) + " " + (j + 1));
						map[j][i] = DOT_O; // Ставим символ "O" на эту ячейку, чтобы блокировать игрока
						return;
					}
					map[j][i] = DOT_EMPTY; // Возвращаем значение ячейки на пустое
				}
			}
		}
		
		do {
			x = rand.nextInt(SIZE);
			y = rand.nextInt(SIZE);
		} while (!isCellValid(x, y));
		System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
		map[y][x] = DOT_O;
	}
	public static void humanTurn() {
		int x, y;
		do {
			System.out.println("Введите координаты в формате X Y");
			x = sc.nextInt() - 1;
			y = sc.nextInt() - 1;
		} while (!isCellValid(x, y));
		map[y][x] = DOT_X;
	}
	public static boolean isCellValid(int x, int y) {
		if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
		if (map[y][x] == DOT_EMPTY) return true;
		return false;
	}
	public static void initMap() {
		map = new char[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				map[i][j] = DOT_EMPTY;
			}
		}
	}
	public static void printMap() {
		for (int i = 0; i <= SIZE; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < SIZE; i++) {
			System.out.print((i + 1) + " ");
			for (int j = 0; j < SIZE; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
