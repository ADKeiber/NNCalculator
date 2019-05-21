import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Austin Keiber
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {

        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();

        if (top.compareTo(bottom) < 0) {
            view.updateSubtractAllowed(false);
        } else {
            view.updateSubtractAllowed(true);
        }

        //checks to see if bottom is bigger than the max int level
        // or if bottom <= 0 then it hides power and root
        if (bottom.compareTo(INT_LIMIT) > 0
                || bottom.compareTo(new NaturalNumber1L(1)) <= 0) {
            view.updatePowerAllowed(false);
            view.updateRootAllowed(false);
        } else {
            view.updatePowerAllowed(true);
            view.updateRootAllowed(true);
        }

        //if bottom is not equal to 0 then you can divide otherwise you can't
        if (bottom.compareTo(new NaturalNumber1L(0)) != 0) {
            view.updateDivideAllowed(true);
        } else {
            view.updateDivideAllowed(false);
        }

        //if bottom pane is less than or equal to 1 allow power to be accessed
        if (bottom.compareTo(new NaturalNumber1L(1)) <= 0) {
            view.updatePowerAllowed(true);
        }
        //updates the view to agree with the model
        view.updateBottomDisplay(bottom);
        view.updateTopDisplay(top);

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();

        /*
         * Update model in response to this event
         */
        top.copyFrom(bottom);

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();

        /*
         * Update model in response to this event
         */
        bottom.add(top);
        top.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSubtractEvent() {

        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();

        /*
         * Update model in response to this event
         */
        top.subtract(bottom);
        bottom.transferFrom(top);

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();

        /*
         * Update model in response to this event
         */
        top.multiply(bottom);
        bottom.transferFrom(top);

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();

        /*
         * Update model in response to this event
         */
        NaturalNumber remainder = top.divide(bottom);
        bottom.transferFrom(top);
        top.transferFrom(remainder);

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processPowerEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();

        /*
         * Update model in response to this event
         */
        top.power(bottom.toInt());
        bottom.transferFrom(top);

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();

        /*
         * Update model in response to this event
         */
        top.root(bottom.toInt());
        bottom.transferFrom(top);

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        /*
         * Get aliases to top and bottom from model and makes a NN with value of
         * digit
         */
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber top = this.model.top();
        NaturalNumber nnDigit = new NaturalNumber1L(digit);

        /*
         * Update model in response to this event
         */
        if (bottom.compareTo(new NaturalNumber1L(0)) == 0) {
            bottom.increment();
            bottom.multiply(nnDigit);
        } else {
            bottom.multiplyBy10(digit);
        }

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }
}
