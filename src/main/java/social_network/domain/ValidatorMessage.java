package social_network.domain;

import social_network.repo.Network;
/**
 * Validator for Message entities.
 */
public class ValidatorMessage implements Validator<Message>{
    public ValidatorMessage() {
    }

    private final Validator<Message> validator = ms -> {
        if(ms.getFromId().equals(ms.getToId())) {
            throw new IllegalArgumentException("The sender and receiver cannot be the same person");
        }
    };

    @Override
    public void validate(Message entity) {
        validator.validate(entity);
    }

//    @Override
//    public void validate(Message entity) {
//        if(entity.getFromId().equals(entity.getToId())){
//            throw new IllegalArgumentException("The sender and the receiver cannot be the same person!");
//        }
//        if(!network.getAllUsers().contains(entity.getFromId()) || !network.getAllUsers().contains(entity.getToId())){
//            throw new IllegalArgumentException("The sender or the receiver does not exist in the network!");
//        }


//        int gasit1 = 0, gasit2 = 0;
//        for(User user : network.getAllUsers()){
//            if(user.getId() == entity.getFromId()){
//                gasit1 = 1;
//            }
//            if(user.getId() == entity.getToId()){
//                gasit2 = 1;
//            }
//            if(gasit1 == 1 && gasit2 == 1){
//                return;
//            }
//        }
//        throw new IllegalArgumentException("The sender or the receiver does not exist in the network!");
    //}
}
