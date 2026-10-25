package social_network.console;

import social_network.domain.*;
import social_network.observable.EventHistory;
import social_network.observable.UserHistory;
import social_network.repo.Network;
import social_network.repo.PersistedNetwork;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/*
 * This is the main class for the social network console application.
 * It provides a command-line interface for users to interact with the social network.
 * Users can add, remove, and update users, manage friendships, send messages,
 * and view user and event history.
 */
public class Main {
    private PersistedNetwork network = new PersistedNetwork();
    private UserHistory userHistory = new UserHistory();
    private EventHistory eventHistory = new EventHistory();

    public Main() {
        this.network.loadFromFile("data/users.csv", "data/friendships.csv", "data/messages.csv");
        network.addObserver(userHistory);
        network.addObserver(eventHistory);
    }
    public static void main(String[] args) {
        Main main = new Main();
        main.help();

        Scanner scanner = new Scanner(System.in);

        while(true) {
            //FAC INTR-O CLASA CONSOLA SI APELEZ APOI AICI
            try {


                main.help();
                String choice;
                System.out.print("Enter a choice: ");
                choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.print("Enter first name: ");
                        String firstnameToAdd = scanner.nextLine();
                        System.out.print("Enter last name: ");
                        String lastnameToAdd = scanner.nextLine();
                        long id = System.currentTimeMillis();
                        User newUser = new User(id, lastnameToAdd, firstnameToAdd);
                        main.network.addUser(newUser);
                        System.out.println("User added.");
                        break;
                    case "2":

                        System.out.print("Enter username id: ");
                        String usernameIDToRemove = scanner.nextLine();
                        long idToRemove = Long.parseLong(usernameIDToRemove);
                        main.network.removeUser(idToRemove);
                        System.out.println("User removed.");
                        break;
                    case "3":
                        System.out.print("Enter new last name: ");
                        String newLastname = scanner.nextLine();
                        System.out.print("Enter new first name: ");
                        String newFirstname = scanner.nextLine();
                        System.out.print("Enter id user: ");
                        String idUser = scanner.nextLine();
                        long idToUpdate = Long.parseLong(idUser);
                        User u = new User(idToUpdate, newFirstname, newLastname);
                        main.network.updateUser(u);
                        System.out.println("User updated.");
                        break;
                    case "4":
                        System.out.print("Enter first id: ");
                        String user1 = scanner.nextLine();
                        Long id1 = Long.parseLong(user1);
                        System.out.print("Enter second id: ");
                        String user2 = scanner.nextLine();
                        Long id2 = Long.parseLong(user2);
                        System.out.println("Enter date and time of friendship (yyyy-MM-ddTHH:mm): ");
                        String dateString = scanner.nextLine();
                        LocalDateTime date = LocalDateTime.parse(dateString);
                        long max_id = 0;
                        for(Friendship fr: main.network.getAllFriendshipList()) {
                            if (fr.getId() > max_id) {
                                max_id = fr.getId();
                            }
                        }
                        main.network.addFriendship(max_id, id1, id2, date);
                        System.out.println("Friendship added.");
                        break;
                    case "5":
                        System.out.print("Enter first id: ");
                        String userToRemove1 = scanner.nextLine();
                        Long idToRemove1 = Long.parseLong(userToRemove1);
                        System.out.print("Enter second id: ");
                        String userToRemove2 = scanner.nextLine();
                        Long idToRemove2 = Long.parseLong(userToRemove2);
                        main.network.removeFriendship(idToRemove1, idToRemove2);
                        System.out.println("Friendship removed.");
                        break;
                    case "6":
                        Iterable<User> users = main.network.getAllUsers();
                        System.out.println("Users:");
                        for (User user : users) {
                            System.out.println("ID: " + user.getId() + ", Name: " + user.getFirstName() + " " + user.getLastname());
                        }
                        break;
                    case "7":
                        Iterable<Friendship> friendships  = main.network.getAllFriendshipList();
                        System.out.println("Friendships:");
                        for(Friendship f: friendships) {
                            System.out.println(f);
                        }
//                        Map<Long, Set<Long>> friendships = main.network.getAllFriendships();
//                        System.out.println("Users and their friends:");
//                        for (Map.Entry<Long, Set<Long>> entry : friendships.entrySet()) {
//                            Long userId = entry.getKey();
//                            Set<Long> friends = entry.getValue();
//                            System.out.println("User ID: " + userId + ", Friends: " + friends);
//                        }
                        break;

                    case "8":
                        List<Message> messages = main.network.getAllMessages();
                        System.out.println("Messages:");
                        for (Message message : messages) {
                            System.out.println("ID: " + message.getId() + ", From: " + message.getFromId() + ", To: "
                                    + message.getToId() + ", Content: " + message.getMessage());
                        }
                        break;
                    case "9":
                        List<String> userHistoryList = main.userHistory.getUserHistory();
                        System.out.println("User History:");
                        for (String history : userHistoryList) {
                            System.out.println(history);
                        }
                        break;
                    case "10":
                        List<String> eventHistoryList = main.eventHistory.getEventHistory();
                        System.out.println("Event History:");
                        for (String history : eventHistoryList) {
                            System.out.println(history);
                        }
                        break;
                    case "11":
                        main.help();
                        break;
                    case "12":
                        System.out.print("Enter message id: ");
                        int messageId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter sender id: ");
                        long senderId = Long.parseLong(scanner.nextLine());

                        System.out.print("Enter receiver id: ");
                        long receiverId = Long.parseLong(scanner.nextLine());

                        System.out.print("Enter message: ");
                        String messageContent = scanner.nextLine();

                        Message message = new Message(
                                messageId,
                                Math.toIntExact(senderId),
                                Math.toIntExact(receiverId),
                                messageContent
                        );

                        main.network.addMessage(message);
                        System.out.println("Message added.");
                        break;

                    case "13":
                        System.out.print("Enter message id to remove: ");
                        int messageIdToRemove = Integer.parseInt(scanner.nextLine());

                        Message messageToRemove = null;
                        for (Message currentMessage : main.network.getAllMessages()) {
                            if (currentMessage.getId().equals(messageIdToRemove)) {
                                messageToRemove = currentMessage;
                                break;
                            }
                        }

                        if (messageToRemove == null) {
                            System.out.println("Message with id " + messageIdToRemove + " does not exist.");
                        } else {
                            main.network.removeMessages(messageToRemove);
                            System.out.println("Message removed.");
                        }
                        break;
                    case "14":
                        int numCommunities = main.network.numberOfCommunities();
                        System.out.println("Number of communities: " + numCommunities);
                        break;
                    case "15":
                        Set<Long> mostSociableCommunity = main.network.mostSociableCommunity();
                        System.out.println("Most sociable community: " + mostSociableCommunity);
                        break;
                    case "16":
                        System.out.print("Enter start date (yyyy-MM-ddTHH:mm): ");
                        String startDateStr = scanner.nextLine();
                        System.out.print("Enter end date (yyyy-MM-ddTHH:mm): ");
                        String endDateStr = scanner.nextLine();
                        List<String> userHistoryBetweenDates = main.userHistory.getUserHistoryBetween(
                                LocalDateTime.parse(startDateStr),
                                LocalDateTime.parse(endDateStr)
                        );
                        System.out.println("User history between " + startDateStr + " and " + endDateStr + ":");
                        for (String history : userHistoryBetweenDates) {
                            System.out.println(history);
                        }
                        break;
                    case "17":
                        System.out.print("Enter start date (yyyy-MM-dd): ");
                        String startEventDateStr = scanner.nextLine();
                        System.out.print("Enter end date (yyyy-MM-dd): ");
                        String endEventDateStr = scanner.nextLine();
                        List<String> eventHistoryBetweenDates = main.eventHistory.getEventHistoryBetween(
                                LocalDateTime.parse(startEventDateStr),
                                LocalDateTime.parse(endEventDateStr)
                        );
                        System.out.println("Event history between " + startEventDateStr + " and " + endEventDateStr + ":");
                        for (String history : eventHistoryBetweenDates) {
                            System.out.println(history);
                        }
                        break;
                    case "0":
                        main.network.saveToFile("data/users.csv", "data/friendships.csv", "data/messages.csv");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void help() {
        System.out.println("Available commands:");
        System.out.println("1. add_user <username>");
        System.out.println("2. remove_user <username>");
        System.out.println("3. update user <old_username> <new_username>");
        System.out.println("4. add_friendship <username1> <username2>");
        System.out.println("5. remove_friendship <username1> <username2>");
        System.out.println("6. list_users");
        System.out.println("7. list_friendships");
        System.out.println("8. list_messages");
        System.out.println("9. list_user_history");
        System.out.println("10. list_event_history");
        System.out.println("11. help");
        System.out.println("12. add_message <message_id> <sender_id> <receiver_id> <message>");
        System.out.println("13. remove_message <message_id>");
        System.out.println("14. numar de comunitati");
        System.out.println("15. cea mai sociabila comunitate");
        System.out.println("16. user history intre doua date");
        System.out.println("17. event history intre doua date");
        System.out.println("0. exit");
    }
}