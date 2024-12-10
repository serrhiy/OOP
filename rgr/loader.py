from dataclasses import dataclass

to_number = lambda bytes: int.from_bytes(bytes, 'little')

@dataclass
class BMPFileHeader:
  filetype: str
  filesize: int
  reserved1: int
  reserved2: int
  offset: int

@dataclass
class BMPInfoHeader:
  infosize: int
  image_width: int
  image_height: int
  planes: int
  bits_per_pixel: int
  compression: int
  image_size: int
  bi_y_pels_per_meter: int
  bi_x_pels_per_meter: int
  clr_used: int
  clr_important: int

headers_sections = (2, 4, 2, 2, 4)
info_sections = [4] * 3 + [2] * 2 + [4] * 6

class BMPFile:
  def __init__(self, filename: str):
    self.filename = filename
    with open(filename, 'rb') as file:
      (type, *header) = map(lambda s: file.read(s), headers_sections)
      info = map(lambda s: to_number(file.read(s)), info_sections)
      filetype = type.decode().lower()
      self.file_header = BMPFileHeader(filetype, *header)
      self.file_info = BMPInfoHeader(*info)
      self.image = file.read(self.file_info.image_size)

__all__ = ['BMPFile']
