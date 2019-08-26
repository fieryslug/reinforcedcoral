package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.core.problem.ProblemMine;
import com.fieryslug.reinforcedcoral.core.problem.ProblemNull;
import com.fieryslug.reinforcedcoral.minigame.match.ProblemMatch;
import com.fieryslug.reinforcedcoral.minigame.minesweeper.ProblemMineSweeper;
import com.fieryslug.reinforcedcoral.minigame.snake.ProblemSnake;

//all the dirty work here
public class WorkTable {

    public static Game getGame0() {

        Game game = new Game(1, 2, 3, 12);

        Category catPhysMath = new Category("數學/物理", "physmath");
        Category catChemBio = new Category("化學/生物", "chembio");
        Category catSocio = new Category("歷史/地理/公民", "socio");
        Category catLit = new Category("國文/英文", "lit");

        Problem prbS5 = new Problem("1/physmath/3.json", "3");
        Problem prbW6 = new Problem("1/physmath/1_1.json", "1_1");
        Problem prbF7 = new Problem("1/physmath/2_1.json", "2_1");
        Problem prbBear = new Problem("1/physmath/4_1.json", "4_1");
        Problem prbLog = new Problem("1/physmath/5.json", "5");
        Problem prbCongee = new Problem("1/physmath/6.json", "6");

        Problem prbSunset = new Problem("1/chembio/1_2.json", "1_2");
        Problem prbFood = new Problem("1/chembio/2_1.json", "2_1");
        Problem prbSofa = new Problem("1/chembio/3.json", "3");
        Problem prbLongan = new Problem("1/chembio/4_1.json", "4_1");
        Problem prbBone = new Problem("1/chembio/5.json", "5");
        Problem prbSoda = new Problem("1/chembio/6_1.json", "6_1");

        Problem prbFamily1 = new Problem("1/socio/1_2.json", "1_2");
        Problem prbElect = new Problem("1/socio/2.json", "2");
        Problem prbCrab = new Problem("1/socio/3.json", "3");
        Problem prbMine = new ProblemMine("<html><strong><font color=red>地雷(認真的)</font></strong></html>");
        Problem prbMoney = new Problem("1/socio/4.json", "4");
        Problem prbBoat = new Problem("1/socio/5_1.json", "5_1");

        prbMine.setShortId("mine");

        Problem prbFamily2 = new Problem("1/lit/1_2.json", "1_2");
        Problem prbPoemSunset = new Problem("1/lit/2_1.json", "2_1");
        Problem prbOdin = new Problem("1/lit/3.json", "3");
        Problem prbPPB = new Problem("1/lit/4_1.json", "4_1");
        Problem prbVocab = new Problem("1/lit/5.json", "5");
        Problem prbHumble = new Problem("1/lit/6.json", "6");

        prbW6.addDependence(prbS5);
        prbF7.addDependence(prbW6);
        prbCrab.addDependence(prbOdin);
        prbFamily2.addDependence(prbFamily1);
        prbBoat.addDependence(prbLog);

        catPhysMath.addProblems(prbS5, prbW6, prbF7, prbBear, prbLog, prbCongee);
        catChemBio.addProblems(prbSunset, prbFood, prbSofa, prbLongan, prbBone, prbSoda);
        catSocio.addProblems(prbFamily1, prbElect, prbCrab, prbMine, prbMoney, prbBoat);
        catLit.addProblems(prbFamily2, prbPoemSunset, prbOdin, prbPPB, prbVocab, prbHumble);

        game.addCategory(catPhysMath);
        game.addCategory(catChemBio);
        game.addCategory(catSocio);
        game.addCategory(catLit);

        game.getProblemSet().setName("Oblivion-1");
        game.getProblemSet().loadResources();
        game.getProblemSet().normalize();

        //System.out.println(prbPPB.exportAsJson().toString(4));

        return game;

    }

    public static Game getGame1() {

        Game game = new Game(1, 2, 3, 4);

        Category catNehs = new Category("實中(基礎)", "nehs");
        Category catNehs2 = new Category("實中(進階)", "nehs2");
        Category catLifeHax = new Category("生活常識", "funfacts");
        Category catAvocado = new Category("Fiery Avocado 3", "avocado");

        Problem prb01 = new Problem("2/nehs/1.json", "1");
        Problem prb02 = new Problem("2/nehs/2.json", "2");
        Problem prb03 = new Problem("2/nehs/3.json", "3");
        Problem prb04 = new Problem("2/nehs/4.json", "4");
        Problem prb05 = new Problem("2/nehs/5.json", "5");
        Problem prb06 = new Problem("2/nehs/6.json", "6");

        Problem prb11 = new Problem("2/nehs2/1.json", "1");
        Problem prb12 = new Problem("2/nehs2/2.json", "2");
        Problem prb13 = new Problem("2/nehs2/3.json", "3");
        Problem prb14 = new Problem("2/nehs2/4.json", "4");
        Problem prb15 = new Problem("2/nehs2/5.json", "5");
        Problem prb16 = new Problem("2/nehs2/6.json", "6");

        Problem prb21 = new Problem("2/funfacts/1.json", "1");
        Problem prb22 = new Problem("2/funfacts/2.json", "2");
        Problem prb23 = new Problem("2/funfacts/3.json", "3");
        Problem prb24 = new Problem("2/funfacts/4.json", "4");
        Problem prb25 = new Problem("2/funfacts/5.json", "5");
        Problem prb26 = new Problem("2/funfacts/6.json", "6");


        Problem prb31 = new Problem("2/avocado/1.json", "1");
        Problem prbMine = new ProblemMineSweeper("踩地雷");
        Problem prb33 = new Problem("2/avocado/3.json", "3");
        Problem prbMatch = new ProblemMatch("記憶遊戲");
        Problem prbSlipper = new ProblemNull("<html><font color=3333ff><strong>左腳拖鞋</strong></font></html>");
        Problem prbSnake = new ProblemSnake("貪食蛇");

        prbMine.setShortId("minesweeper");
        prbMatch.setShortId("match");
        prbSlipper.setShortId("slipper");
        prbSnake.setShortId("snake");

        prb11.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb12.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb13.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb14.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb15.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);
        prb16.addDependence(prb01, prb02, prb03, prb04, prb05, prb06);

        prbMine.addDependence(prb31);
        prbMatch.addDependence(prb33);
        prbSnake.addDependence(prb11);


        catNehs.addProblems(prb01, prb02, prb03, prb04, prb05, prb06);
        catNehs2.addProblems(prb11, prb12, prb13, prb14, prb15, prb16);
        catLifeHax.addProblems(prb21, prb22, prb23, prb24, prb25, prb26);
        catAvocado.addProblems(prb31, prbMine, prb33, prbMatch, prbSlipper, prbSnake);

        game.addCategory(catNehs);
        game.addCategory(catNehs2);
        game.addCategory(catLifeHax);
        game.addCategory(catAvocado);

        game.getProblemSet().setName("Oblivion-2");
        game.getProblemSet().loadResources();
        game.getProblemSet().normalize();

        return game;
    }


}
