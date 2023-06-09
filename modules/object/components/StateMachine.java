package hiof.gameenigne2d.modules.object.components;

import hiof.gameenigne2d.modules.object.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StateMachine extends Component {
    private class StateTrigger {
        private String state;
        private String trigger;

        public StateTrigger() {

        }
        public StateTrigger(String state, String trigger) {
            this.state = state;
            this.trigger = trigger;
        }

        @Override
        public boolean equals(Object object) {
            if (object.getClass() != StateTrigger.class) return false;
            StateTrigger t2 = (StateTrigger) object;
            return t2.trigger.equals(this.trigger) && t2.state.equals(this.state);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trigger, state);
        }

        @Override
        public String toString() {
            return state + " " + trigger;
        }
    }
    private HashMap<StateTrigger, String> stateTransfers = new HashMap<>();
    private List<AnimationState> states = new ArrayList<>();
    private transient AnimationState currentState = null;
    private String defaultStateTitle = "";

    /**
     * Creates a state machine that holds all the animation states that belongs to the GameObject
     * and manages the trigger that switch between them
     */
    public StateMachine() {

    }

    /**
     * Refreshes the textures in the animation state
     */
    public void refreshTextures() {
        for (AnimationState state : states) {
            state.refreshTextures();
        }
    }

    /**
     * Adds a trigger that changes from one state to another and gives it a name
     * @param from animation state that is changed from
     * @param to animation state that is changed to
     * @param onTrigger name of the trigger
     */
    public void addStateTrigger(String from, String to, String onTrigger) {
        this.stateTransfers.put(new StateTrigger(from, onTrigger), to);
    }

    /**
     * Adds an animation state to the GameObjects state machine
     * @param state the animation state to be added
     */
    public void addState(AnimationState state) {
        this.states.add(state);
    }

    public void trigger(String trigger) {
        for (StateTrigger state : stateTransfers.keySet()) {
            if (currentState != null) {
                if (state.state.equals(currentState.getTitle()) && state.trigger.equals(trigger)) {
                    if (stateTransfers.get(state) != null) {
                        int newStateIndex = stateIndexOf(stateTransfers.get(state));
                        if (newStateIndex > -1) {
                            currentState = states.get(newStateIndex);
                        }
                    }
                    return;
                }
            }
        }
        System.out.println("Could not find trigger " + trigger);
    }

    private int stateIndexOf(String stateTitle) {
        int index = 0;
        for (AnimationState state : states) {
            if (state.getTitle().equals(stateTitle)) {
                return index;
            }
            index++;
        }

        return -1;
    }

    /**
     * Sets the default animation state of the state machine
     * @param animationTitle the name of the animation state
     */
    public void setDefaultState(String animationTitle) {
        for (AnimationState state : states) {
            if (state.getTitle().equals(animationTitle)) {
                defaultStateTitle = animationTitle;
                if (currentState == null) {
                    currentState = state;
                    return;
                }
            }
        }
        System.out.println("Unable to find default state");
    }

    @Override
    public void start() {
        for (AnimationState state : states) {
            if (state.getTitle().equals(defaultStateTitle)) {
                currentState = state;
                break;
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        if (currentState != null) {
            currentState.update(deltaTime);
            SpriteRenderer sprite = gameObject.getComponent(SpriteRenderer.class);
            if (sprite != null) {
                sprite.setSprite(currentState.getCurrentSprite());
                sprite.setTexture(currentState.getCurrentSprite().getTexture());
            }
        }
    }

    public HashMap<StateTrigger, String> getStateTransfers() {
        return stateTransfers;
    }

    public List<AnimationState> getStates() {
        return states;
    }

    public AnimationState getCurrentState() {
        return currentState;
    }
}
