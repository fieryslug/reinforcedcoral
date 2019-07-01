package com.fieryslug.reinforcedcoral.core;

//all the dirty work here
public class WorkTable {

    public static Game getGame() {

        Game game = new Game(1, 2, 3, 12);

        Category catPhysMath = new Category("數學/物理");
        Category catChemBio = new Category("化學/生物");
        Category catSocio = new Category("歷史/地理/公民");
        Category catLit = new Category("國文/英文");

        //coralcoralcoralcoralcoral-----------------------------------------------------------

        Problem prbFlight1 = new Problem("飛機的原理1", 40);
        prbFlight1.addPages(new Page("hi"));
        Problem prbFlight2 = new Problem("飛機的原理2", 50);
        Problem prbFlight3 = new Problem("飛機的原理3", 30);
        Problem prb04 = new Problem("place holder", 20);
        Problem prb05 = new Problem("place holder", 20);
        Problem prb06 = new Problem("place holder", 20);

        Problem prbFlight = new Problem("飛行的原理", 30);
        Problem prb12 = new Problem("place holder", 20);
        Problem prb13 = new Problem("place holder", 20);
        Problem prb14 = new Problem("place holder", 20);
        Problem prb15 = new Problem("place holder", 20);
        Problem prb16 = new Problem("place holder", 20);

        Problem prb21 = new Problem("place holder", 20);
        Problem prb22 = new Problem("place holder", 20);
        Problem prb23 = new Problem("place holder", 20);
        Problem prb24 = new Problem("place holder", 20);
        Problem prb25 = new Problem("place holder", 20);
        Problem prb26 = new Problem("place holder", 20);

        Problem prbPoemPaint = new Problem("我們的油漆", 90);
        Problem prbPoemSunset = new Problem("夕陽前發生的事", 70);
        Problem prb33 = new Problem("place holder", 20);
        Problem prb34 = new Problem("place holder", 20);
        Problem prb35 = new Problem("place holder", 20);
        Problem prb36 = new Problem("place holder", 20);

        catPhysMath.addProblem(prbFlight1, prbFlight2, prbFlight3, prb04, prb05, prb06);
        catChemBio.addProblem(prbFlight, prb12, prb13, prb14, prb15, prb16);
        catSocio.addProblem(prb21, prb22, prb23, prb24, prb25, prb26);
        catLit.addProblem(prbPoemPaint, prbPoemSunset, prb33, prb34, prb35, prb36);

        game.addCategory(catPhysMath);
        game.addCategory(catChemBio);
        game.addCategory(catSocio);
        game.addCategory(catLit);

        return game;

    }

}
