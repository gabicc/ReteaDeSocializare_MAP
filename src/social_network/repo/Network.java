package social_network.repo;

import social_network.domain.*;
import social_network.events.*;
import social_network.exceptions.ExistingFriendshipException;
import social_network.exceptions.FriendshipDoesntExist;
import social_network.exceptions.UserDoesntExistException;
import social_network.exceptions.ExistingUserException;
import social_network.observable.SocialObservable;
import social_network.repo.file.FriendshipFileRepository;
import social_network.repo.file.MessageFileRepository;
import social_network.repo.file.UserFileRepository;

import java.time.LocalDateTime;
import java.util.*;

public class Network extends SocialObservable {
    //private List<User> users = new ArrayList<User>();
    //private List<Friendship> friendshipList = new ArrayList<Friendship>();
    //private List<Message> messages = new ArrayList<Message>();
    private ValidatorUser validatorUser = new ValidatorUser(this);
    private ValidatorFriendship validatorFriendship = new ValidatorFriendship(this);
    private ValidatorMessage validatorMessage = new ValidatorMessage(this);
    private UserFileRepository userFileRepository = new UserFileRepository(validatorUser, "data/users.csv");
    private FriendshipFileRepository friendshipFileRepository = new FriendshipFileRepository(validatorFriendship, "data/friendships.csv");
    private MessageFileRepository messageFileRepository = new MessageFileRepository(validatorMessage, "data/messages.csv");

    public Network() {}

    public Iterable<User> getAllUsers() {
        return userFileRepository.findAll();
    }

    public Iterable<Friendship> getAllFriendshipList() {
        return friendshipFileRepository.findAll();
    }

//    public Map<Long, Set<Long>> getAllFriendships() {
//        return friendships;
//    }

    public Iterable<Message> getAllMessages() {
        return messageFileRepository.findAll();
    }

    public void addUser(User u) {
        validatorUser.validate(u);

        userFileRepository.save(u);
        notify(new UserAddEvent(u));
    }

    public User removeUser(long id) {
        // This method verifies if a user exists and it deletes it if yes
        List<User> newUserList = new ArrayList<>();
        for(User u: userFileRepository.findAll()) {
            newUserList.add(u);
        }
        for(User us: newUserList) {
            if(us.getId().equals(id)) {
                List<Friendship> newFriendshipList = new ArrayList<>();
                for(Friendship f: friendshipFileRepository.findAll()) {
                    newFriendshipList.add(f);
                }
                for(Friendship f: newFriendshipList) {
                    if(f.getId1() == id || f.getId2() == id) {
                        friendshipFileRepository.delete(f.getId());
                    }
                }
//                List<Friendship> newFriendshipList = new ArrayList<>(friendshipList);
//                newFriendshipList.addAll(friendshipList);
//                for(Friendship f: newFriendshipList) {
//                    if(f.getId1() == id || f.getId2() == id) {
//                        removeFriendship(f.getId1(), f.getId2());
//                    }
//                }

                List<Message> newMessage = new ArrayList<>();
                for(Message m: newMessage) {
                    if(m.getFromId().equals(us.getId()))
                        messageFileRepository.delete(m.getId());
                }

                userFileRepository.delete(us.getId());
                notify(new UserRemoveEvent(us));
                return us;
//                Set<Long> friends =  friendships.getOrDefault(us.getId(), new HashSet<>());
//                Set<Long> newFriendsList = new HashSet<>();
//                newFriendsList.addAll(friends);
//                for(Long friendID: newFriendsList) {
//                    removeFriendship(id, friendID);
//                }
//
//                List<Message> newMessage = new ArrayList<>();
//                for(Message m: newMessage) {
//                    if(m.getFromId().equals(us.getId()))
//                        messages.remove(m);
//                }
//
//                users.remove(us);
//                notify(new UserRemoveEvent(us));
//                return us;
            }
        }
        throw new UserDoesntExistException("This user cannot be removed due to the lack of atoms that it has(it doesnt exist)");
    }

    public void updateUser(User u) {
        userFileRepository.update(u);
        notify(new UserUpdateEvent(u));
        // nu mai stiu ce sa pun aici
//        int gasit = 0;
//        for(User us: userFileRepository.findAll()) {
//            if(us.getId().equals(u.getId())){
//                gasit = 1;
//                us.setFirtName(u.getFirstName());
//                us.setLastname(u.getLastname());
//                notify(new UserUpdateEvent(u));
//            }
//        }
//        if(gasit == 0)
//            throw new UserDoesntExistException("User cannot be updated because it's not in the list");
    }

    //getall, crud firendship, message crud
//    public void addFriendship(long id1, long id2, LocalDateTime date) {
//        // nu mai stiu ce sa pun
//        Set<Long> a = friendships.getOrDefault(id1, new HashSet<Long>());
//        Friendship f = new Friendship(id1, id2, date);
//        validatorFriendship.validate(f);
//        if(a.contains(id2))
//            System.out.println("Friendship between user with id: " + id1 + " and user with id: " + id2 + " already exists");
//        a.add(id2);
//        friendships.put(id1, a);
//
//        Set<Long> b = friendships.getOrDefault(id2, new HashSet<Long>());
//        if(b.contains(id1))
//            System.out.println("Friendship between user with id: " + id1 + " and user with id: " + id2 + " already exists");
//        b.add(id1);
//        friendships.put(id2, b);
//        friendshipList.add(f);
//
//        notify(new FriendshipAddEvent(f));
//    }

    public void addFriendship(long id, long id1, long id2, LocalDateTime date) {
        long max_id = 0;
        for(Friendship fr: friendshipFileRepository.findAll()) {
            if (fr.getId() > max_id) {
                max_id = fr.getId();
            }
        }
        Friendship f = new Friendship( max_id + 1, id1, id2, date);
        friendshipFileRepository.save(f);

        notify(new FriendshipAddEvent(f));
    }

//    public void removeFriendship(long id1, long id2) {
//        // mi-e lene
//        Set<Long> a = friendships.getOrDefault(id1, new HashSet<Long>());
//
//        if(!a.contains(id2)){
//            throw new FriendshipDoesntExist("Friendship doesnt exist");
//        }
//        a.remove(id2);
//        LocalDateTime date_friendship = null;
//        for (Friendship f: friendshipList) {
//            if((f.getId1() == id1 && f.getId2() == id2) || (f.getId1() == id2 && f.getId2() == id1)) {
//                date_friendship = f.getDate();
//                break;
//            }
//        }
//        friendshipList.remove(new Friendship(id1, id2, date_friendship));
//
//        Set<Long> b = friendships.getOrDefault(id2, new HashSet<Long>());
//        if(!b.contains(id1)){
//            throw new FriendshipDoesntExist("Friendship doesnt exist");
//        }
//        b.remove(id1);
//        friendshipList.remove(new Friendship(id2, id1, date_friendship));
//
//        notify(new FriendshipRemoveEvent(new Friendship(id1, id2, date_friendship)));
//    }

    public void removeFriendship(long id1, long id2) {
        for(Friendship f: friendshipFileRepository.findAll()) {
            if(f.getId1() == id1 && f.getId2() == id2) {
                friendshipFileRepository.delete(f.getId());
                notify(new FriendshipRemoveEvent(f));
                return;
            }
            if(f.getId1() == id2 && f.getId2() == id1) {
                friendshipFileRepository.delete(f.getId());
                notify(new FriendshipRemoveEvent(f));
                return;
            }
        }
    }

    public void addMessage(Message m) {
        validatorMessage.validate(m);
        messageFileRepository.save(m);
        notify(new MessageAddEvent(m));
    }

    public void removeMessages(Message messageToRemove) {
        // cam atat
        Message removed = messageFileRepository.delete(messageToRemove.getId());

        if (removed == null) {
            throw new IllegalArgumentException(
                    "Message with id " + messageToRemove.getId() + " does not exist.");
        }

        notify(new MessageRemoveEvent(messageToRemove));
    }

    public List<Message> userAllSentMessages(long id){
        List<Message> ms = new ArrayList<Message>();
        for(Message m: messageFileRepository.findAll()) {
            if(m.getFromId() == id) {
                ms.add(m);
            }
        }
        return ms;
    }

    public List<Message> userAllReceivedMessages(long id){
        List<Message> ms = new ArrayList<Message>();
        for(Message m: messageFileRepository.findAll()) {
            if(m.getToId() == id) {
                ms.add(m);
            }
        }
        return ms;
    }

    public Set<Long> userAllFriendsIds(long id) {
        Set<Long> friends = new HashSet<>();
        for(Friendship f: friendshipFileRepository.findAll()) {
            if(f.getId1() == id) {
                friends.add(f.getId2());
            }
            if(f.getId2() == id) {
                friends.add(f.getId1());
            }
        }
        return friends;
    }

    public Set<Long> getUserCommunity(long id) {
        //long V = friendships.size();
        Map<Long, Boolean> vizited = new HashMap<Long, Boolean>();
        for(User u: userFileRepository.findAll()) {
            vizited.put(u.getId(), false);
        }

        Set<Long> res = new HashSet<Long>();

        Queue<Long> q = new LinkedList<>();
        q.add(id);
        while(!q.isEmpty()) {
            long current = q.poll();
            res.add(current);
            vizited.put(current, true);
            Set<Long> friends = userAllFriendsIds(current);
            for(Long i: friends) {
                if(vizited.get(i) == false) {
                    vizited.put(i, true);
                    q.add(i);
                }
            }
        }
        return res;
    }

    private Long firstNotVisited(Map<Long, Boolean> visited) {
        for(Map.Entry<Long, Boolean> entry: visited.entrySet()) {
            if(!entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public List<Set<Long>> getAllCommunities() {
        // nu mai am chef
        Map<Long, Boolean> visited = new HashMap<Long, Boolean>();
        List<Set<Long>> rezList = new ArrayList<>();
        for(User u: userFileRepository.findAll()) {
            visited.put(u.getId(), false);
        }

        Queue<Long> q = new LinkedList<>();
        Long first = firstNotVisited(visited);
        while(first != null) {
            Set<Long> res = new HashSet<Long>();
            q.add(first);
            while (!q.isEmpty()) {
                long current = q.poll();
                res.add(current);
                visited.put(current, true);
                Set<Long> friends = userAllFriendsIds(current);
                for (Long i : friends) {
                    if (visited.get(i) == false) {
                        visited.put(i, true);
                        q.add(i);
                    }
                }
            }
            rezList.add(res);
            first = firstNotVisited(visited);
        }
        return rezList;
    }

    public Integer numberOfCommunities() {
        List<Set<Long>> rezList = getAllCommunities();
        return rezList.size();
    }

    public Set<Long> mostSociableCommunity() {
        List<Set<Long>> rezList = getAllCommunities();
        long maxx = 0;
        Set<Long> finn = new HashSet<>();
        for(Set<Long> res: rezList) {
            if(res.size()>maxx) {
                maxx = res.size();
                finn = res;
            }
        }
        return finn;
    }
}
