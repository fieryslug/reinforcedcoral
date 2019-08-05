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
        Problem prbS5 = new Problem("1/physmath/3.json");
        Problem prbEquations = new Problem("1/physmath/4.json");
        Problem prbLog = new Problem("1/physmath/5.json");
        Problem prbCongee = new Problem("1/physmath/6.json");



        Problem prbSlime = new Problem("1/chembio/1_1.json");
        Problem prbSnakeEel = new Problem("1/chembio/2_1.json");
        Problem prbRiver = new Problem("1/chembio/3.json");
        Problem prbWalnut = new Problem("1/chembio/4.json");
        Problem prbChemBio = new Problem("1/chembio/5.json");
        Problem prb16 = new Problem("1/chembio/6.json");

        Problem prbShark = new Problem("1/socio/1.json");
        Problem prbElect = new Problem("1/socio/2.json");
        Problem prbCrab = new Problem("1/socio/3.json");
        Problem prb24 = new ProblemMine("<html><font color=red>地雷(認真的)</font></html>");
        Problem prb25 = new Problem("1/socio/4.json");
        Problem prb26 = new Problem("1/socio/5.json");

        Problem prbPoemPaint = new Problem("1/lit/1.json");
        Problem prbPoemSunset = new Problem("1/lit/2.json");
        Problem prbOdin = new Problem("1/lit/3.json");
        Problem prbBull = new Problem("1/lit/4.json");
        Problem prb35 = new Problem("1/lit/5.json");
        Problem prbHumble = new Problem("1/lit/6.json");

        prbFlight2.addDependence(prbFlight1);
        prbCrab.addDependence(prbOdin);

        catPhysMath.addProblem(prbFlight1, prbFlight2, prbS5, prbEquations, prbLog, prbCongee);
        catChemBio.addProblem(prbSlime, prbSnakeEel, prbRiver, prbWalnut, prbChemBio, prb16);
        catSocio.addProblem(prbShark, prbElect, prbCrab, prb24, prb25, prb26);
        catLit.addProblem(prbPoemPaint, prbPoemSunset, prbOdin, prbBull, prb35, prbHumble);

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
