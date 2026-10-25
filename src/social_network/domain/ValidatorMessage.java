package social_network.domain;

import social_network.repo.Network;
/**
 * Validator for Message entities.
 */
public class ValidatorMessage extends Validator<Message>{
    public ValidatorMessage(Network network) {
        super(network);
    }

    @Override
    public void validate(Message entity) {
        if(entity.getFromId().equals(entity.getToId())){
            throw new IllegalArgumentException("The sender and the receiver cannot be the same person!");
        }
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
    }
}
