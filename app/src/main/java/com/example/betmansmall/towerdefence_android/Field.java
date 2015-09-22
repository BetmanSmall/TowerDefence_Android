package com.example.betmansmall.towerdefence_android;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by betmansmall on 18.09.2015.
 */
public class Field {
    class Cell {
        int step;
        boolean empty;
        boolean busy;

//        bool spawn; // NEED Check!!!!! ?????  Check
//        bool exit; // NEED Check!!!!! ????? // NEED Check!!!!! ????? // NEED Check!!!!! ?????

        //    Tower* tower;
//        bool tower;
//        vector<Creep*> creeps;
        Creep creep;

//        QPixmap backgroundPixmap;
//        QPixmap busyPixmap;

        Cell() {
            this.step = 0;
            this.empty = true;
            this.busy = false;
        }
    };

    Cell field[];
//    Towers towers;
    Creeps creeps;
//    Faction* faction1;

//    boolean creepSet;

    int gameOverLimitCreeps;
    int currentFinishedCreeps;

//    int sizeWidgetWidth;
//    int sizeWidgetHeight;

    int sizeX, sizeY;

    int mainCoorMapX, mainCoorMapY;
    int spaceWidget;
    int sizeCell;

//    int mouseX, mouseY;
//    int spawnPointX, spawnPointY;
//    int exitPointX, exitPointY;

    Field() {
        field = null;
    }

    void createField(int newSizeX, int newSizeY) {
        if(field == null) {
            int size = newSizeX * newSizeY;

            this.field = new Cell[size];
            for(int k = 0; k < size; k++) { this.field[k] = new Cell(); }

            this.creeps = new Creeps(30);

//            this.creepSet = true;

            this.sizeX = newSizeX;
            this.sizeY = newSizeY;

            this.mainCoorMapX = 0;
            this.mainCoorMapY = 0;
            this.spaceWidget = 0;
            this.sizeCell = 32;

//            mouseX = -1;
//            mouseY = -1;
//            spawnPointX = -1;
//            spawnPointY = -1;
//            exitPointX = -1;
//            exitPointY = -1;
        } else {
            deleteField();
            createField(newSizeX, newSizeY);
        }
    }

    void deleteField() {
        if(field != null) {
            int size = getSizeX() * getSizeY();
            for(int k = 0; k < size; k++) {
                this.field[k] = null;
            }
            field = null;
//            towers.deleteField();
            creeps.deleteMass();
        }
    }

//    void Field::setFaction(Faction* faction);

//    boolean createSpawnPoint(int num, int x, int y) {
//        for(int k = 0; k < creeps.getAmount(); k++)
//        {
//            Creep creep = creeps.getCreep(k);
//            int creepX = creep.coorByCellX;
//            int creepY = creep.coorByCellY;
//            clearCreep(creepX, creepY);
//        }
//
//        if(x == -1 && y == -1)
//        {
//            if(!isSetSpawnPoint())
//                return false;
//        }
//        else
//        {
//            spawnPointX = x;
//            spawnPointY = y;
////        field[sizeX*y+x].spawn = true; // BAGS!!!!!
////        field[sizeX*y+x].empty = false; // BAGS!!!!!
//            clearBusy(x,y); // BAGS!!!!!
//        }
//        creeps.deleteMass();
//        creeps.createMass(num);
//        currentFinishedCreeps = 0;
//        return true;
//    }
//    void Field::createExitPoint(int x, int y);

    int getSizeX() {
        return sizeX;
    }

    int getSizeY() {
        return sizeY;
    }

    void setMainCoorMap(int mainCoorMapX, int mainCoorMapY) {
        this.mainCoorMapX = mainCoorMapX;
        this.mainCoorMapY = mainCoorMapY;
    }

    void setSizeCell(int sizeCell) {
        this.sizeCell = sizeCell;
    }

    int getMainCoorMapX() {
        return mainCoorMapX;
    }

    int getMainCoorMapY() {
        return mainCoorMapY;
    }

    int getSpaceWidget() {
        return spaceWidget;
    }

    int getSizeCell() {
        return sizeCell;
    }

//    bool Field::towersAttack();
//    void Field::waveAlgorithm(int x, int y);
//    void Field::waveStep(int x, int y, int step);
//    void Field::setMousePress(int x, int y);
//    bool Field::getMousePress(int x, int y);
//    bool Field::isSetSpawnPoint(int x, int y);
//    bool Field::isSetExitPoint(int x, int y);
//    int Field::stepAllCreeps();
//    int Field::stepOneCreep(int num);

    int getNumStep(int x, int y) {
        if(x >= 0 && x < getSizeX()) {
            if(y >= 0 && y < getSizeY()) {
                if(!containBusy(x, y)) {
                    if(!containTower(x, y)) {
                        return getStepCell(x, y);
                    }
                }
            }
        }
        return 0;
    }

    int getStepCell(int x, int y) {
        return field[sizeX*y + x].step;
    }

    boolean setNumOfCell(int x, int y, int step) {
        if(x >= 0 && x < getSizeX()) {
            if(y >= 0 && y < getSizeY()) {
                if(!containBusy(x, y) && !containTower(x, y)) {
                    if(getStepCell(x, y) > step || getStepCell(x, y) == 0) {
                        setStepCell(x, y, step);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void setStepCell(int x, int y, int step) {
        field[sizeX*y + x].step = step;
    }

    void clearStepCell(int x, int y) {
        field[sizeX*y + x].step = 0;
    }

//    Creep* Field::getCreep(int x, int y);
//    std::vector<Creep*> Field::getCreeps(int x, int y);
//    int Field::getCreepHpInCell(int x, int y);
//    Creep* Field::getCreepWithLowHP(int x, int y);
//    std::vector<Tower*> Field::getAllTowers();

    boolean containEmpty(int x, int y) {
        return field[sizeX*y + x].empty;
    }

    boolean containBusy(int x, int y) {
        return field[sizeX*y + x].busy;
    }

    boolean containTower(int x, int y) {
        return false;
    }

    boolean containCreep(int x, int y) {
//        return containCreep(x, y, null);
        if(field[sizeX*y + x].creep != null) {
            return true;
        } else {
            return false;
        }
    }

    boolean containCreep(int x, int y, Creep creep) {
//        if(!field[sizeX*y + x].creeps.empty())
        if(field[sizeX*y + x].creep != null) {
//            int size = field[sizeX*y + x].creeps.size();
            if(creep == null) {
//                return size;
                return true;
            } else {
                return true;
//                for (int k = 0; k < size; k++) {
////                    if (field[sizeX * y + x].creeps[k] == creep) {
//                    if (field[sizeX * y + x].creep == creep) {
//                        return k + 1;
//                    }
//                }
            }
        }
//    if(field[sizeX*y + x].creep != NULL)
//        if(field[sizeX*y + x].creep->alive)
//            return true;
//        return 0;
        return false;
    }

    boolean setBusy(int x, int y) {//, QPixmap pixmap) {
        Log.d("TTW", "setBusy(1) -- x: " + x + " y: " + y);
        if(containEmpty(x, y)) {
//            Log.d("TTW", "setBusy(2) -- x: " + x + " y: " + y);
            field[sizeX*y + x].busy = true;
            field[sizeX*y + x].empty = false;
//            if(!pixmap.isNull())
//                field[sizeX*y + x].busyPixmap = pixmap;
//            Log.d("TTW", "setBusy(3) -- x: " + x + " y: " + y);
            return true;
        } else {
//            Log.d("TTW", "setBusy(4) -- x: " + x + " y: " + y);
            return false;
        }
    }

//    bool Field::setTower(int x, int y);
//    bool Field::setTower(int x, int y, DefaultTower* defTower);
//    bool Field::setCreepInSpawnPoint();

    boolean setCreep(int x, int y) {
        Log.d("TTW", "setCreep(1) -- x: " + x + " y: " + y);
        return setCreep(x, y, null);
    }
    boolean setCreep(int x, int y, Creep creep) {
//    if(x == -1 && y == -1)
//        return setCreep(spawnPointX, spawnPointY, creep);//, type);

        if(field[sizeX*y + x].empty || field[sizeX*y + x].creep != null) {
            if(creep == null) {
//                int coorByMapX = mainCoorMapX + spaceWidget + x*sizeCell;
//                int coorByMapY = mainCoorMapY + spaceWidget + y*sizeCell;
//
//                Creep creep;
//                if(creepSet)
//                    creep = creeps.createCreep(x, y, coorByMapX, coorByMapY, faction1->getDefaultUnitById(0));
//                else
//                    creep = creeps.createCreep(x, y, coorByMapX, coorByMapY, faction1->getDefaultUnitById(1));
//                creepSet = !creepSet;
//
//                if(creep == NULL)
//                    return false;
//                field[sizeX*y + x].creeps.push_back(creep);

                Log.d("TTW", "setCreep(2) -- field[" + sizeX*y + x + "].creep: " + field[sizeX*y + x].creep);
                field[sizeX*y + x].creep = creeps.createCreep(x, y);
                Log.d("TTW", "setCreep(3) -- field[" + sizeX*y + x + "].creep: " + field[sizeX*y + x].creep);
                if(field[sizeX*y + x].creep == null) {
                    return false;
                }
            } else {
//                field[sizeX*y + x].creeps.push_back(creep);
                field[sizeX * y + x].creep = creep;
            }
            field[sizeX*y + x].empty = false;
            return true;
        }
        return false;
    }

    boolean clearBusy(int x, int y) {
        Log.d("TTW", "clearBusy(1) -- x: " + x + " y: " + y);
        if(!containEmpty(x, y)) {
            Log.d("TTW", "clearBusy(2) -- x: " + x + " y: " + y);
            if(containBusy(x, y)) {
                Log.d("TTW", "clearBusy(3) -- x: " + x + " y: " + y);
                field[sizeX*y + x].busy = false;
                field[sizeX*y + x].empty = true;
                Log.d("TTW", "clearBusy(4) -- x: " + x + " y: " + y);
                return true;
            }
        }
        Log.d("TTW", "clearBusy(5) -- x: " + x + " y: " + y);
        return false;
    }

//    bool Field::clearTower(int x, int y);
    boolean clearCreep(int x, int y) {
        return clearCreep(x, y, null);
    }

    boolean clearCreep(int x, int y, Creep creep) {
        if(!field[sizeX*y + x].empty) {
            if(creep == null) {
//                field[sizeX*y + x].creeps.clear();
                field[sizeX*y + x].creep = null;
//            } else if(int num = containCreep(x, y, creep) {
//                field[sizeX*y + x].creeps.erase(field[sizeX*y + x].creeps.begin()+(num-1));
//            field[sizeX*y + x].creep = NULL;
            } else if(containCreep(x, y, creep)) {
                field[sizeX*y + x].creep = null;
            }

//            if(field[sizeX*y + x].creeps.empty())
            if(field[sizeX*y + x].creep == null) {
                field[sizeX * y + x].empty = true;
            }
            return true;
        }
        return false;
    }
//    bool Field::deleteTower(int x, int y);
//    void Field::setPixmapInCell(int x, int y, QPixmap pixmap);
//    void Field::setPixmapForCreep(QPixmap pixmap);
//    void Field::setPixmapForTower(QPixmap pixmap);
//    QPixmap Field::getBusyPixmapOfCell(int x, int y);
//    QPixmap Field::getPixmapOfCell(int x, int y);
//    QPixmap Field::getCreepPixmap(int x, int y);
//    QPixmap Field::getTowerPixmap(int x, int y);
}
