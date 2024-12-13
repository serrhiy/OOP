import json

class Config:

  @staticmethod
  def from_file(filename):
    with open(filename) as file:
      content = json.load(file)
      return Config(content)

  def __init__(self, config: dict):
    for key, value in config.items():
      data = Config(value) if isinstance(value, dict) else value
      setattr(self, key, data)

  def to_dict(self):
    result = {}
    for key, value in self.__dict__.items():
      data = value.to_dict() if isinstance(value, Config) else value
      result[key] = data
    return result
