package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.page.Page;

//all the dirty work here
public class WorkTable {

    public static Game getGame() {

        Game game = new Game(1, 2, 3, 12);

        Category catPhysMath = new Category("數學/物理");
        Category catChemBio = new Category("化學/生物");
        Category catSocio = new Category("歷史/地理/公民");
        Category catLit = new Category("國文/英文");

        //coralcoralcoralcoralcoral-----------------------------------------------------------

        Problem prbFlight1 = new Problem("1/physmath/1.json");
        Problem prbFlight2 = new Problem("1/physmath/2.json");
        Problem prbFlight3 = new Problem("1/physmath/3.json");
        Problem prbEquations = new Problem("1/physmath/4.json");
        Problem prb05 = new Problem("place holder", 20);
        Problem prb06 = new Problem("place holder", 20);

        prbFlight2.addDependence(prbFlight1);

        Problem prbFlight = new Problem("1/chembio/1.json");
        Problem prbSnakeEel = new Problem("1/chembio/2.json");
        Problem prb13 = new Problem("place holder", 20);
        Problem prb14 = new Problem("place holder", 20);
        Problem prb15 = new Problem("place holder", 20);
        Problem prb16 = new Problem("place holder", 20);

        Problem prbShark = new Problem("1/socio/1.json");
        Problem prbElect = new Problem("1/socio/2.json");
        Problem prb23 = new Problem("place holder", 20);
        Problem prb24 = new Problem("place holder", 20);
        Problem prb25 = new Problem("place holder", 20);
        Problem prb26 = new Problem("place holder", 20);

        Problem prbPoemPaint = new Problem("1/lit/1.json");
        Problem prbPoemSunset = new Problem("1/lit/2.json");
        Problem prb33 = new Problem("place holder", 20);
        Problem prb34 = new Problem("1/lit/4.json");
        Problem prb35 = new Problem("place holder", 20);
        Problem prb36 = new Problem("place holder", 20);

        catPhysMath.addProblem(prbFlight1, prbFlight2, prbFlight3, prbEquations, prb05, prb06);
        catChemBio.addProblem(prbFlight, prbSnakeEel, prb13, prb14, prb15, prb16);
        catSocio.addProblem(prbShark, prbElect, prb23, prb24, prb25, prb26);
        catLit.addProblem(prbPoemPaint, prbPoemSunset, prb33, prb34, prb35, prb36);

        game.addCategory(catPhysMath);
        game.addCategory(catChemBio);
        game.addCategory(catSocio);
        game.addCategory(catLit);

        return game;

    }

    public static Game getGame1() {

        return null;

    }

}
