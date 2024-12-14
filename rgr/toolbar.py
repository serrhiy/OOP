import tkinter as tk
from tkinter import ttk
from collections.abc import Callable
from events.EventEmitter import EventEmitter

def pipe(*functions):
  def wrapped():
    for function in functions: function()
  return wrapped

def wrap_callback(toolBar, name, callback):
  def wrapped():
    toolBar.emit('shape', name)
    callback()
  return wrapped

class ToolBar(tk.Frame, EventEmitter):

  def __init__(self, root, tools: dict[str, Callable]):
    tk.Frame.__init__(self, root, bd=1, relief='raised')
    EventEmitter.__init__(self)
    images = []
    for name, (imagepath, callback) in tools.items():
      command = wrap_callback(self, name, callback)
      image = tk.PhotoImage(file=imagepath)
      button = ttk.Button(self, image=image, command=command)
      button.pack(side=tk.LEFT, ipadx=2, ipady=2)
      images.append(image)

    self.images = images

  def pack(self, **kwargs):
    super().pack(**kwargs, side=tk.TOP, fill=tk.X)
