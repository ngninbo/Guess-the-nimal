package animals.command;

import animals.domain.RandomItem;
import animals.service.NodeService;

public class ExitCommand implements Command {

    private final NodeService nodeService;

    public ExitCommand(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public boolean execute() {

        save();

        sayGoodbye();

        return false;
    }

    public void save() {
        nodeService.saveRoot();
    }

    public void sayGoodbye() {
        System.out.println(new RandomItem<>(format("guess.game.session.farewell").split("\f")).next());
    }
}
