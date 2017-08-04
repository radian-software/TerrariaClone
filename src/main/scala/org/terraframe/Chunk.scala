package org.terraframe;

case class Chunk(cx: Int, cy: Int) {

    val rv = World.generateChunk(cx, cy, TerraFrame.getRandom());
    
    val blocks:Array3D[Int] = rv(0).asInstanceOf[Array3D[Int]];
    val blockds:Array3D[Byte] = rv(1).asInstanceOf[Array3D[Byte]];
    val blockdns:Array2D[Byte] = rv(2).asInstanceOf[Array2D[Byte]];
    val blockbgs:Array2D[Byte] = rv(3).asInstanceOf[Array2D[Byte]];
    val blockts:Array2D[Byte] = rv(4).asInstanceOf[Array2D[Byte]];
    val lights:Array2D[Float] = rv(5).asInstanceOf[Array2D[Float]];
    val power:Array3D[Float] = rv(6).asInstanceOf[Array3D[Float]];
    val lsources:Array2D[Boolean] = rv(7).asInstanceOf[Array2D[Boolean]];
    val zqn:Array2D[Byte] = rv(8).asInstanceOf[Array2D[Byte]];
    val pzqn:Array3D[Byte] = rv(9).asInstanceOf[Array3D[Byte]];
    val arbprd:Array3D[Boolean] = rv(10).asInstanceOf[Array3D[Boolean]];
    val wcnct:Array2D[Boolean] = rv(11).asInstanceOf[Array2D[Boolean]];
    val drawn:Array2D[Boolean] = rv(12).asInstanceOf[Array2D[Boolean]];
    val rdrawn:Array2D[Boolean] = rv(13).asInstanceOf[Array2D[Boolean]];
    val ldrawn:Array2D[Boolean] = rv(14).asInstanceOf[Array2D[Boolean]];

}
