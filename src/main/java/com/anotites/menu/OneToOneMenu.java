package com.anotites.menu;

import com.anotites.pojos.oneToOneExample.EmployeeDetail;
import com.anotites.pojos.oneToOneExample.EmployeeForTableForOneToOne;
import com.anotites.service.OneToOneService;
import com.anotites.util.HibernateUtil;

import java.util.Scanner;

public class OneToOneMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final OneToOneService service = new OneToOneService();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = readInt("Введите номер операции: ");
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> addEmployeeDetail();
                case 3 -> deleteEmployee();
                case 4 -> deleteEmployeeDetail();
                case 5 -> findEmployeeById();
                case 6 -> findEmployeeDetailById();
                case 0 -> {
                    exit = true;
                    System.out.println("Выход из программы.");
                }
                default -> System.out.println("Неверный пункт меню. Попробуйте снова.");
            }
        }
        HibernateUtil.close(); // закрываем EntityManagerFactory
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("1. Добавить сотрудника");
        System.out.println("2. Добавить детали сотрудника");
        System.out.println("3. Удалить сотрудника по ID");
        System.out.println("4. Удалить детали по ID");
        System.out.println("5. Найти сотрудника по ID");
        System.out.println("6. Найти детали по ID");
        System.out.println("0. Выход");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка: введите целое число.");
            scanner.next(); // очистка неверного ввода
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private static void addEmployee() {
        System.out.print("Введите возраст: ");
        int age = readInt("Возраст: ");
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Фамилия: ");
        String surname = scanner.nextLine();

        EmployeeForTableForOneToOne employee = new EmployeeForTableForOneToOne(
                null, age, name, surname, null);

        // Если нужно сохранить вместе с деталями, можно создать детали и установить связь
        // В простом варианте сохраняем только сотрудника без деталей
        service.saveEmployee(employee);
        System.out.println("Сотрудник добавлен с ID: " + employee.getId());
    }

    private static void addEmployeeDetail() {
        System.out.println("Сохранение деталей для существующего сотрудника.");
        int employeeId = readInt("Введите ID сотрудника: ");
        EmployeeForTableForOneToOne employee = service.loadEmployee(employeeId);
        if (employee == null) {
            System.out.println("Сотрудник с ID " + employeeId + " не найден.");
            return;
        }
        System.out.print("Номер дома: ");
        String number = scanner.nextLine();
        System.out.print("Улица: ");
        String street = scanner.nextLine();
        System.out.print("Город: ");
        String city = scanner.nextLine();

        // Проверяем, есть ли уже детали
        if (employee.getEmployeeDetail() != null) {
            // Обновляем существующие детали
            EmployeeDetail existingDetail = employee.getEmployeeDetail();
            existingDetail.setNumber(number);
            existingDetail.setStreet(street);
            existingDetail.setCity(city);
            System.out.println("Детали обновлены.");
        } else {
            // Создаём новые детали
            EmployeeDetail newDetail = new EmployeeDetail(null, number, street, city, employee);
            employee.setEmployeeDetail(newDetail);
            System.out.println("Детали добавлены.");
        }

        // Сохраняем сотрудника (каскадно сохранит/обновит детали)
        service.saveEmployee(employee);
    }

    private static void deleteEmployee() {
        int id = readInt("Введите ID сотрудника для удаления: ");
        service.deleteEmployee(id);
        System.out.println("Операция удаления выполнена.");
    }

    private static void deleteEmployeeDetail() {
        int id = readInt("Введите ID деталей для удаления: ");
        service.deleteEmployeeDetail(id);
        System.out.println("Операция удаления выполнена.");
    }

    private static void findEmployeeById() {
        int id = readInt("Введите ID сотрудника: ");
        EmployeeForTableForOneToOne emp = service.loadEmployee(id);
        if (emp != null) {
            System.out.println("Найден: " + emp);
        } else {
            System.out.println("Сотрудник не найден.");
        }
    }

    private static void findEmployeeDetailById() {
        int id = readInt("Введите ID деталей: ");
        EmployeeDetail detail = service.loadEmployeeDetail(id);
        if (detail != null) {
            System.out.println("Найдены детали: " + detail);
        } else {
            System.out.println("Детали не найдены.");
        }
    }
}
