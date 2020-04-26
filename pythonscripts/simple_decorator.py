def paragraph_wrap(func):
 def func_wrapper(name):
  return "<p>{0}</p>".format(func(name))
 return func_wrapper
@paragraph_wrap
def get_text(name):
 return "lorem ipsum, {0} dolor sit amet".format(name)
print get_text("John")