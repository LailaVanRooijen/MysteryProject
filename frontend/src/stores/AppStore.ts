import { action, makeObservable, observable } from "mobx";

import { Store } from "@app/lib/Store";

export default class AppStore extends Store {
  counter = 0;

  constructor() {
    super();
    makeObservable(this, {
      counter: observable,
      increment: action,
      decrement: action,
    });
  }

  increment() {
    this.counter = this.counter + 1;
    console.log(this.counter);
  }

  decrement() {
    this.counter = this.counter - 1;
    console.log(this.counter);
  }
}
