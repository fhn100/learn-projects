package org.learn.java.concurrent.design_pattern.chapter11;

public class ExecutionTask implements Runnable {

    private QueryFromDBAction queryAction = new QueryFromDBAction();

    private QueryFromHttpAction httpAction = new QueryFromHttpAction();

    @Override
    public void run() {

        queryAction.execute();
        System.out.println("The name query successful");
        httpAction.execute();
        System.out.println("The card id query successful");

        Context context = ActionContext.getActionContext().getContext();
        System.out.println("The Name is " + context.getName() + " and CardId " + context.getCardId());
    }
}