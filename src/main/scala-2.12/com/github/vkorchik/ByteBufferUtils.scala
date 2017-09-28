package com.github.vkorchik

import java.nio.ByteBuffer

object ByteBufferUtils {

  def bbConvert(buffer: ByteBuffer) = {
    buffer.array()
  }

}
