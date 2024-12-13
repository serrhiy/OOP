import tkinter as tk
from tkinter import ttk
from collections.abc import Callable

class ToolBar(tk.Frame):

  def __init__(self, root, tools: dict[str, Callable]):
    super().__init__(root, bd=1, relief='raised')
    images = []
    for imagepath, callback in tools.items():
      image = tk.PhotoImage(file=imagepath)
      button = ttk.Button(self, image=image, command=callback)
      button.pack(side=tk.LEFT, ipadx=2, ipady=2)
      images.append(image)
    self.images = images
  
  def pack(self, **kwargs):
    super().pack(**kwargs, side=tk.TOP, fill=tk.X)
