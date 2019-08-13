package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.minigame.minesweeper.ProblemMineSweeper;

//all the dirty work here
public class WorkTable {

    public static Game getGame() {

        Game game = new Game(1, 2, 3, 12);

        Category catPhysMath = new Category("數學/物理");
        Category catChemBio = new Category("化學/生物");
        Category catSocio = new Category("歷史/地理/公民");
        Category catLit = new Category("國文/英文");

        Problem prbS5 = new Problem("1/physmath/3.json");
        Problem prbW6 = new Problem("1/physmath/1_1.json");
        Problem prbF7 = new Problem("1/physmath/2_1.json");
        Problem prbBear = new Problem("1/physmath/4_1.json");
        Problem prbLog = new Problem("1/physmath/5.json");
        Problem prbCongee = new Problem("1/physmath/6.json");

        Problem prbSunset = new Problem("1/chembio/1_2.json");
        Problem prbFood = new Problem("1/chembio/2_1.json");
        Problem prbSofa = new Problem("1/chembio/3.json");
        Problem prbLongan = new Problem("1/chembio/4_1.json");
        Problem prbBone = new Problem("1/chembio/5.json");
        Problem prbSoda = new Problem("1/chembio/6_1.json");

        Problem prbFamily1 = new Problem("1/socio/1_2.json");
        Problem prbElect = new Problem("1/socio/2.json");
        Problem prbCrab = new Problem("1/socio/3.json");
        Problem prbMine = new ProblemMine("<html><strong><font color=red>地雷(認真的)</font></strong></html>");
        Problem prbMoney = new Problem("1/socio/4.json");
        Problem prbBoat = new Problem("1/socio/5_1.json");

        Problem prbFamily2 = new Problem("1/lit/1_2.json");
        Problem prbPoemSunset = new Problem("1/lit/2_1.json");
        Problem prbOdin = new Problem("1/lit/3.json");
        Problem prbPPB = new Problem("1/lit/4_1.json");
        Problem prbVocab = new Problem("1/lit/5.json");
        Problem prbHumble = new Problem("1/lit/6.json");

        prbW6.addDependence(prbS5);
        prbF7.addDependence(prbW6);
        prbCrab.addDependence(prbOdin);
        prbFamily2.addDependence(prbFamily1);
        prbBoat.addDependence(prbLog);

        catPhysMath.addProblem(prbS5, prbW6, prbF7, prbBear, prbLog, prbCongee);
        catChemBio.addProblem(prbSunset, prbFood, prbSofa, prbLongan, prbBone, prbSoda);
        catSocio.addProblem(prbFamily1, prbElect, prbCrab, prbMine, prbMoney, prbBoat);
        catLit.addProblem(prbFamily2, prbPoemSunset, prbOdin, prbPPB, prbVocab, prbHumble);

        game.addCategory(catPhysMath);
        game.addCategory(catChemBio);
        game.addCategory(catSocio);
        game.addCategory(catLit);

        return game;

    }

    public static Game getGame1() {

        Game game = new Game(1, 2, 3, 4);

        Category catNehs = new Category("實中(基礎)");
        Category catNehs2 = new Category("實中(進階)");
        Category catLifeHax = new Category("生活常識");
        Category catAvocado = new Category("Fiery Avocado 3");

        Problem prb01 = new Problem("2/nehs/1.json");
        Problem prb02 = new Problem("2/nehs/2.json");
        Problem prb03 = new Problem("2/nehs/3.json");
        Problem prb04 = new Problem("2/nehs/4.json");
        Problem prb05 = new Problem("2/nehs/5.json");
        Problem prb06 = new Problem("2/nehs/6.json");

        Problem prb11 = new Problem("2/nehs2/1.json");
        Problem prb12 = new Problem("2/nehs2/2.json");
        Problem prb13 = new Problem("2/nehs2/3.json");
        Problem prb14 = new Problem("2/nehs2/4.json");
        Problem prb15 = new Problem("2/nehs2/5.json");
        Problem prb16 = new Problem("2/nehs2/6.json");

        Problem prb21 = new Problem("2/funfacts/1.json");
        Problem prb22 = new Problem("2/funfacts/2.json");
        Problem prb23 = new Problem("2/funfacts/3.json");
        Problem prb24 = new Problem("2/funfacts/4.json");
        Problem prb25 = new Problem("2/funfacts/5.json");
        Problem prb26 = new Problem("2/funfacts/6.json");

        Problem prb31 = new ProblemMineSweeper("踩地雷");
        Problem prbSnake = new ProblemSnake("貪食蛇");
        Problem prb33 = new ProblemMineSweeper("瘋狂踩地雷");
        Problem prb34 = new ProblemMatch("神經衰弱");
        Problem prb35 = new Problem("placeholder", 0);
        Problem prb36 = new ProblemSlipper("<html><font color=3333ff><strong>左腳拖鞋</strong></font></html>");

        prb11.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb12.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb13.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb14.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb15.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb16.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);

        prbSnake.addDependence(prb11);


        catNehs.addProblem(prb01, prb02, prb03, prb04, prb05, prb06);
        catNehs2.addProblem(prb11, prb12, prb13, prb14, prb15, prb16);
        catLifeHax.addProblem(prb21, prb22, prb23, prb24, prb25, prb26);
        catAvocado.addProblem(prb31, prbSnake, prb33, prb34, prb35, prb36);

        game.addCategory(catNehs);
        game.addCategory(catNehs2);
        game.addCategory(catLifeHax);
        game.addCategory(catAvocado);
        return game;
    }

}
