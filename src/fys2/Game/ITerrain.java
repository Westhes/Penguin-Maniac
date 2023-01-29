/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Game;

import processing.core.PVector;

/**
 *
 * @author Westhes
 */
interface ITerrain
{
  //public PVector getPointAtX(float x);
  public PVector getRawPointAtX(float x);
}