package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int operation;
        double[][] A;
        double[][] B;

        do {
            System.out.println("""
                            1. Add matrices
                            2. Multiply matrix by a constant
                            3. Multiply matrices
                            4. Transpose matrix
                            5. Calculate a determinant
                            6. Inverse matrix
                            0. Exit""");
            System.out.print("Your choice:");
            operation = scanner.nextInt();
            switch (operation) {
                case 1 -> {
                    System.out.print("Enter size of first matrix:");
                    A = read(scanner);
                    System.out.print("Enter size of second matrix:");
                    B = read(scanner);
                    printMatrix(addition(A, B));
                }
                case 2 -> {
                    System.out.print("Enter size of first matrix:");
                    A = read(scanner);
                    System.out.print("Enter constant:");
                    printMatrix(multiplicationWithConstant(A, scanner.nextDouble()));
                }
                case 3 -> {
                    System.out.print("Enter size of first matrix:");
                    A = read(scanner);
                    System.out.print("Enter size of second matrix:");
                    B = read(scanner);
                    printMatrix(matrixMultiplication(A, B));
                }
                case 4 -> {
                    System.out.println("""
                            1. Main diagonal
                            2. Side diagonal
                            3. Vertical line
                            4. Horizontal line
                            """);
                    System.out.print("Your choice:");
                    int choice = scanner.nextInt();
                    System.out.print("Enter matrix size:");
                    A = read(scanner);
                    transpose(A, choice);
                }
                case 5 -> {
                    System.out.print("Enter matrix size:");
                    System.out.println("The result is:\n" + determinant(read(scanner)));
                }
                case 6 -> {
                    System.out.print("Enter matrix size:");
                    A = inverseMatrix(read(scanner));
                    if (A == null) {
                        System.out.println("This matrix doesn't have an inverse.");
                    } else {
                        printMatrix(A);
                    }
                }
            }
        } while (operation != 0);

    }

    private static double[][] read(Scanner scanner) {
        int row = scanner.nextInt();
        int column = scanner.nextInt();
        double[][] matrix = new double[row][column];

        System.out.println("Enter matrix:");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return matrix;
    }

    private static double[][] addition(double[][] A, double[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) {
            return null;
        }

        int row = A.length;
        int column = A[0].length;
        double[][] matrix = new double[row][column];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++) {
                matrix[i][j] = A[i][j] + B[i][j];
            }
        }

        return matrix;
    }

    private static double[][] multiplicationWithConstant(double[][] matrix, double number) {
        int row = matrix.length;
        int column = matrix[0].length;
        double[][] result = new double[row][column];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++) {
                result[i][j] = matrix[i][j] * number;
            }
        }

        return result;
    }

    private static double[][] matrixMultiplication(double[][] A, double[][] B) {
        if (A[0].length != B.length) {
            return null;
        }
        int row = A.length;
        int column = B[0].length;
        double[][] result = new double[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }

    private static void printMatrix(double[][] matrix) {
        if (matrix == null) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        System.out.println("The result is:");
        for (double[] numbers : matrix) {
            for (double n : numbers) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }

    private static double[][] mainDiagonal(double[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        double[][] result = new double[column][row];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    private static double[][] sideDiagonal(double[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        double[][] result = new double[column][row];

        for (int i = row - 1; i >= 0; --i) {
            for (int j = column - 1; j >= 0; --j) {
                result[column - 1 - j][row - 1 - i] = matrix[i][j];
            }
        }

        return result;
    }

    private static double[][] verticalLine(double[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        double[][] result = new double[row][column];

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                result[i][column - 1 - j] = matrix[i][j];
            }
        }

        return result;
    }

    private static double[][] horizontalLine(double[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        double[][] result = new double[row][column];

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                result[row - 1 - i][j] = matrix[i][j];
            }
        }

        return result;
    }

    private static void transpose(double[][] matrix, int choice) {
        switch (choice) {
            case 1 -> printMatrix(mainDiagonal(matrix));
            case 2 -> printMatrix(sideDiagonal(matrix));
            case 3 -> printMatrix(verticalLine(matrix));
            case 4 -> printMatrix(horizontalLine(matrix));
        }
    }

    private static double[][] getCofactor(double[][] matrix, int row, int column){
        double[][] det = new double[matrix.length - 1][matrix.length - 1];
        int countRow = 0;
        int countColumn = 0;


        for (int i = 0; i < matrix.length; i++) {
            if (i == row) {
                continue;
            }
            for (int j = 0; j < matrix[0].length; j++) {
                if (j == column) {
                    continue;
                }
                det[countRow][countColumn++] = matrix[i][j];
            }
            countRow++;
            countColumn = 0;
        }
        return det;
    }

    private static double determinant(double[][] matrix) {
        double det = 0;
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        for (int i = 0; i < matrix.length; i++) {
            det += Math.pow(-1, i) * matrix[0][i] * determinant(getCofactor(matrix, 0, i));
        }
        return det;
    }

    private static double[][] inverseMatrix(double[][] matrix) {
        double det = determinant(matrix);

        if (det == 0) {
            return null;
        }

        return multiplicationWithConstant(matrixConsistingOfCofactors(mainDiagonal(matrix)), 1 / det );
    }

    private static double[][] matrixConsistingOfCofactors(double[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        double[][] result = new double[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result[i][j] = Math.pow(-1, i + j) * determinant(getCofactor(matrix, i, j));
            }
        }

        return result;
    }
}

