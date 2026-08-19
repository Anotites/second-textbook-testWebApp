package com.anotites.menu;

import com.anotites.pojos.Relationship.Department;
import com.anotites.pojos.Relationship.EmployeeDetail;
import com.anotites.pojos.Relationship.EmployeeForRelationship;
import com.anotites.pojos.Relationship.Meeting;
import com.anotites.service.RelationshipService;
import com.anotites.util.HibernateUtil;

import java.util.Scanner;

public class RelationshipMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RelationshipService service = new RelationshipService();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = readInt("Введите номер операции: ");
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> addEmployeeDetail();
                case 3 -> addDepartment();
                case 4 -> addEmployeeToDepartment();
                case 5 -> deleteEmployee();
                case 6 -> deleteEmployeeDetail();
                case 7 -> deleteDepartment();
                case 8 -> findEmployeeById();
                case 9 -> findEmployeeDetailById();
                case 10 -> findDepartmentById();
                case 11 -> addMeeting();
                case 12 -> addEmployeeToMeeting();
                case 13 -> deleteMeeting();
                case 14 -> findMeetingId();
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
        System.out.println("3. Добавить отдел");
        System.out.println("4. Добавить сотрудника в отдел");
        System.out.println("5. Удалить сотрудника по ID");
        System.out.println("6. Удалить детали по ID");
        System.out.println("7. Удалить отдел по ID");
        System.out.println("8. Найти сотрудника по ID");
        System.out.println("9. Найти детали по ID");
        System.out.println("10. Найти отдел по ID");
        System.out.println("11. Добавить встречу");
        System.out.println("12. Добавить сотрудника во встречу");
        System.out.println("13. Удалить встречу по ID");
        System.out.println("14. Найти встречу по ID");
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

        EmployeeForRelationship employee = new EmployeeForRelationship(
                null, age, name, surname, null);

        service.saveEmployee(employee);
        System.out.println("Сотрудник добавлен с ID: " + employee.getId());
    }

    private static void addDepartment() {
        System.out.print("Введите название отдела: ");
        String name = scanner.nextLine();

        Department department = new Department(
                null, name, null);

        service.saveDepartment(department);
        System.out.println("Отдел добавлен с ID: " + department.getDepartmentId());
    }

    private static void addEmployeeToDepartment() {
        System.out.println("Добавление отдела для существующего сотрудника.");
        int employeeId = readInt("Введите ID сотрудника: ");
        EmployeeForRelationship employee = service.loadEmployee(employeeId);
        if (employee == null) {
            System.out.println("Сотрудник с ID " + employeeId + " не найден.");
            return;
        }

        int departmentId = readInt("Введите ID отдела: ");
        Department department = service.loadDepartment(departmentId);
        if (department == null) {
            System.out.println("Отдел с ID " + departmentId + " не найден.");
            return;
        }

        // Проверяем, есть ли уже отдел
        if (employee.getDepartment() != null) {
            // Обновляем существующие детали
            Department existingDepartment = employee.getDepartment();
            existingDepartment.setDepartmentName(department.getDepartmentName());
            existingDepartment.setDepartmentId(department.getDepartmentId());
            System.out.println("Детали обновлены.");
        } else {
            // Создаём новые детали
            employee.setDepartment(department);
            System.out.println("Детали добавлены.");
        }
        // Сохраняем сотрудника (каскадно сохранит/обновит детали)
        service.saveEmployee(employee);
    }

    private static void addEmployeeDetail() {
        System.out.println("Сохранение деталей для существующего сотрудника.");
        int employeeId = readInt("Введите ID сотрудника: ");
        EmployeeForRelationship employee = service.loadEmployee(employeeId);
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

    private static void deleteDepartment() {
        int id = readInt("Введите ID отдела для удаления: ");
        service.deleteDepartment(id);
        System.out.println("Операция удаления выполнена.");
    }

    private static void findEmployeeById() {
        int id = readInt("Введите ID сотрудника: ");
        EmployeeForRelationship emp = service.loadEmployee(id);
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

    private static void findDepartmentById() {
        int id = readInt("Введите ID отдела: ");
        Department department = service.loadDepartment(id);
        if (department != null) {
            System.out.println("Найден отдел: " + department);
        } else {
            System.out.println("Отдел не найден.");
        }
    }

    private static void addMeeting() {
        System.out.print("Введите время для встречи: ");
        String time = scanner.nextLine();

        Meeting meeting = new Meeting(
                null, time, null);

        service.saveMeeting(meeting);
        System.out.println("Встреча добавлена с ID: " + meeting.getMeetingId());
    }

    private static void addEmployeeToMeeting() {
        System.out.println("Добавление сотрудника во встречу.");
        int employeeId = readInt("Введите ID сотрудника: ");
        int meetingId = readInt("Введите ID встречи: ");

        service.assignEmployeeToMeeting(employeeId, meetingId);
        System.out.println("Сотрудник добавлен во встречу.");
    }

    private static void deleteMeeting() {
        int id = readInt("Введите ID встречи для удаления: ");
        service.deleteMeeting(id);
        System.out.println("Операция удаления выполнена.");
    }

    private static void findMeetingId() {
        int id = readInt("Введите ID встречи: ");
        Meeting meeting = service.loadMeeting(id);
        if (meeting != null) {
            System.out.println("Найдена встреча: " + meeting);
        } else {
            System.out.println("Встреча не найдена.");
        }
    }
}
