package animals.service;

import animals.domain.Direction;
import animals.model.BinaryTree;
import animals.model.TreeStats;
import animals.model.Node;
import animals.repository.NodeRepository;
import animals.ressource.MessageRessource;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class NodeServiceImpl implements NodeService {

    private final BinaryTree tree = new BinaryTree();

    private final NodeRepository nodeRepository;

    private final MessageRessource messageRessource;

    {
        messageRessource = MessageRessource.getInstance();
    }

    public NodeServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public void add(String value) {
        tree.add(value);
    }

    @Override
    public Node getRoot() {
        return tree.getRoot();
    }

    @Override
    public Stack<String> findAllFacts(String animal) {
        return nodeRepository.findAncestors(tree, animal);
    }

    @Override
    public TreeStats statistics() {
        return nodeRepository.summary(tree);
    }

    @Override
    public boolean delete(String animal) {
        return nodeRepository.remove(animal, tree);
    }

    @Override
    public void addAnimal(Node node, final String statement, final String animal, Direction direction) {
        nodeRepository.update(tree, node, statement, animal, direction);
    }

    @Override
    public boolean loadRoot() {
        try {
            nodeRepository.setRoot(tree);
        } catch (IOException e) {
            System.out.println(getMessage("guess.game.session.loading.failure.text"));
        }

        return !tree.getRoot().isLeaf();
    }

    @Override
    public void saveRoot() {
        try {
            nodeRepository.save(tree);
        } catch (IOException e) {
            System.out.println(getMessage("guess.game.session.save.failure.text"));
        }
    }

    @Override
    public List<String> findAllAnimals() {
        return nodeRepository.findLeafNodes(tree.getRoot());
    }

    private String getMessage(String key) {
        return messageRessource.getProperty(key);
    }
}
