import { vi } from 'vitest';

declare global {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  var jasmine: any;
}

globalThis.jasmine = {
  createSpyObj: (name: string, methodNames: string[]) => {
    const obj: Record<string, any> = {};
    methodNames.forEach(method => {
      obj[method] = vi.fn();
    });
    return obj;
  }
};
