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

        Cell() {
            this.step = 0;
            this.empty = true;
            this.busy = false;
        }
    };

    Cell field[];
    int sizeX, sizeY;
    int mainCoorMapX, mainCoorMapY;
    int spaceWidget;
    int sizeCell;

    Field() {
        field = null;
    }

    void createField(int newSizeX, int newSizeY) {
        if(field == null) {
            int size = newSizeX * newSizeY;
            this.field = new Cell[size];

            for(int k = 0; k < size; k++) {
                this.field[k] = new Cell();
            }

            this.sizeX = newSizeX;
            this.sizeY = newSizeY;
            this.mainCoorMapX = 0;
            this.mainCoorMapY = 0;
            this.sizeCell = 32;
        } else {
            deleteField();
            createField(newSizeX, newSizeY);
        }
    }

    void deleteField() {
        if(field != null) {
            field = null;
        }
    }

//    void Field::setFaction(Faction* faction);
//    bool Field::createSpawnPoint(int num, int x, int y);
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
//    int Field::containCreep(int x, int y, Creep *creep);

    boolean setBusy(int x, int y) {//, QPixmap pixmap) {
        Log.d("TTW", "setBusy(1) -- x: " + x + " y: " + y);
        if(containEmpty(x, y)) {
            Log.d("TTW", "setBusy(2) -- x: " + x + " y: " + y);
            field[sizeX*y + x].busy = true;
            field[sizeX*y + x].empty = false;
//            if(!pixmap.isNull())
//                field[sizeX*y + x].busyPixmap = pixmap;
            Log.d("TTW", "setBusy(3) -- x: " + x + " y: " + y);
            return true;
        } else {
            Log.d("TTW", "setBusy(4) -- x: " + x + " y: " + y);
            return false;
        }
    }

//    bool Field::setTower(int x, int y);
//    bool Field::setTower(int x, int y, DefaultTower* defTower);
//    bool Field::setCreepInSpawnPoint();
//    bool Field::setCreep(int x, int y, Creep* creep);

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
//    bool Field::clearCreep(int x, int y, Creep *creep);
//    bool Field::deleteTower(int x, int y);
//    void Field::setPixmapInCell(int x, int y, QPixmap pixmap);
//    void Field::setPixmapForCreep(QPixmap pixmap);
//    void Field::setPixmapForTower(QPixmap pixmap);
//    QPixmap Field::getBusyPixmapOfCell(int x, int y);
//    QPixmap Field::getPixmapOfCell(int x, int y);
//    QPixmap Field::getCreepPixmap(int x, int y);
//    QPixmap Field::getTowerPixmap(int x, int y);
}
