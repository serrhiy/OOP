from collections.abc import Callable
from typing import Any

type Callback = Callable[[list[Any]], None]
type Listeners = dict[str, list[Callback]]

class EventEmitter:

  def __init__(self):
    self.listeners: Listeners = {}

  def on(self, name: str, listener: Callback):
    listeners = self.listeners.get(name, [])
    listeners.append(listener)
    self.listeners[name] = listeners
    return self

  def emit(self, name: str, *args):
    listeners = self.listeners.get(name, [])
    for listener in listeners: listener(args)
    return self

  def removeAllListeners(self, name):
    if name in self.listeners: self.listeners.pop(name)
    return self

  def removeListener(self, name: str, listener: Callback):
    listeners = self.listeners.get(name, [])
    listeners.remove(listener)
    return self

  def once(self, name: str, listener: Callback):
    def wrapped(*args):
      listener(args)
      self.listeners.get(name, []).remove(wrapped)
    self.on(name, wrapped)
    return self

