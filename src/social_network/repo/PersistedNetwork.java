package social_network.repo;

import social_network.domain.Friendship;
import social_network.domain.Message;
import social_network.domain.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PersistedNetwork extends Network {
    public PersistedNetwork() {
        super();
    }

    private void loadUsers(String filename) {
        /*
            * Reads user data from a file and populates the network with User objects.
         */
        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(filename), StandardCharsets.UTF_8)) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(";", -1);
                if (fields.length != 3) {
                    throw new IllegalArgumentException(
                            "Invalid user format at line " + lineNumber);
                }

                int id = Integer.parseInt(fields[0].trim());
                String lastname = fields[1].trim();
                String firstname = fields[2].trim();

                // Uses Network.addUser, which populates the inherited users list.
                addUser(new User(id, lastname, firstname));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read users file: " + filename, e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid user ID in file: " + filename, e);
        }
    }

    private void loadFriendships(String filename) {
        /*
            * Reads friendship data from a file and populates the network with friendships.
         */
        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(filename), StandardCharsets.UTF_8)) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(";", -1);
                if (fields.length != 4) {
                    throw new IllegalArgumentException(
                            "Invalid friendship format at line " + lineNumber);
                }

                long id = Long.parseLong(fields[0].trim());
                long id1 = Integer.parseInt(fields[1].trim());
                long id2 = Integer.parseInt(fields[2].trim());
                String dateString = fields[3].trim();
                LocalDateTime date = LocalDateTime.parse(dateString);

                // Uses Network.addUser, which populates the inherited users list.
                addFriendship(id, id1, id2, date);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read friendship file: " + filename, e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid friendship ID in file: " + filename, e);
        }
    }

    private void loadMessages(String filename) {
        /*
            * Reads message data from a file and populates the network with Message objects.
         */
        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(filename), StandardCharsets.UTF_8)) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(";", -1);
                if (fields.length != 4) {
                    throw new IllegalArgumentException(
                            "Invalid message format at line " + lineNumber);
                }

                int id1 = Integer.parseInt(fields[1].trim());
                int id2 = Integer.parseInt(fields[2].trim());
                String messageContent = fields[3].trim();

                Message m = new Message(lineNumber, id1, id2, messageContent);
                addMessage(m);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read message file: " + filename, e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid message ID in file: " + filename, e);
        }
    }

    public void loadFromFile(String filenameUsers, String filenameFriendships, String filenameMessages) {
        loadUsers(filenameUsers);
        loadFriendships(filenameFriendships);
        loadMessages(filenameMessages);
    }

    private void saveToFileUsers(String filename) {
        /*
            * Saves user data to a file in CSV format.
         */
        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(filename),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            for (User user : getAllUsers()) {
                writer.write(user.getId() + ";"
                        + user.getLastname() + ";"
                        + user.getFirstName());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Could not write users file: " + filename, e);
        }
    }

    /*
     * Saves friendship data to a file in CSV format.
     */
    private void saveToFileFriendships(String filename) {
        /*
            * Saves friendship data to a file in CSV format.
         */
        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(filename),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            Set<String> savedFriendships = new HashSet<>();
            for(Friendship friendship : getAllFriendshipList()) {
                long id1 = friendship.getId1();
                long id2 = friendship.getId2();
                LocalDateTime date = friendship.getDate();

                long smallerId = Math.min(id1, id2);
                long largerId = Math.max(id1, id2);
                String friendshipKey = smallerId + ";" + largerId + ";" + date.toString();

                // Avoid saving both 1,2 and 2,1.
                if (savedFriendships.add(friendshipKey)) {
                    writer.write(smallerId + ";" + largerId + ";" + date.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Could not write friendship file: " + filename, e);
        }
    }

    private void saveToFileMessages(String filename) {
        /*
            * Saves message data to a file in CSV format.
         */
        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(filename),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Message message : getAllMessages()) {
                writer.write(message.getId() + ";"
                        + message.getFromId() + ";"
                        + message.getToId() + ";"
                        + message.getMessage());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Could not write message file: " + filename, e);
        }
    }

    public void saveToFile(String filenameUsers, String filenameFriendships, String filenameMessages) {
        saveToFileUsers(filenameUsers);
        saveToFileFriendships(filenameFriendships);
        saveToFileMessages(filenameMessages);
    }
}
