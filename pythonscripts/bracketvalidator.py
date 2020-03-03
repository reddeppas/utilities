def is_valid(code):
  openers_to_closers = {
  '[' : ']',
  '{' : '}',
  '(' : ')',
  }
  openers = set(openers_to_closers.keys())
  closers = set(openers_to_closers.values())
  openers_stack = []

  for char in code:
    if char in openers:
      openers_stack.append(char)
    elif char in closers:
      if not openers_stack:
        return False
      else:
        last_unclosed_opener = openers_stack.pop()
        if not openers_to_closers[last_unclosed_opener] == char:
          return False    
  return openers_stack == [] 
def main():
  code = "[}()]"
  status = is_valid(code)
  print status
if __name__=="__main__":
  main()
