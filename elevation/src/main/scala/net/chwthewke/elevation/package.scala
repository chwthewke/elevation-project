package net.chwthewke

import geotrellis.raster.Tile

package object elevation {
  implicit class withTileFoldOps( val self: Tile ) extends AnyVal {
    def foldDouble[A]( z: A )( f: ( A, Double ) => A ): A = {
      var acc: A = z
      self.foreachDouble( x => acc = f( acc, x ) )
      acc
    }

    def foldDoubleAt[A]( z: A )( f: ( A, ( Int, Int, Double ) ) => A ): A = {
      var acc: A = z
      self.foreachDouble( ( col, row, value ) => acc = f( acc, ( col, row, value ) ) )
      acc
    }

    def fold[A]( z: A )( f: ( A, Int ) => A ): A = {
      var acc: A = z
      self.foreach( x => acc = f( acc, x ) )
      acc
    }

    def foldAt[A]( z: A )( f: ( A, ( Int, Int, Int ) ) => A ): A = {
      var acc: A = z
      self.foreach( ( col, row, value ) => acc = f( acc, ( col, row, value ) ) )
      acc
    }
  }

}
