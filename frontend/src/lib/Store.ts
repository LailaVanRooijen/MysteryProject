import {
  IReactionDisposer,
  IReactionOptions,
  IReactionPublic,
  reaction,
} from "mobx";

type ReactionExpression<T> = (r: IReactionPublic) => T;
type ReactionEffect<T, F> = (
  arg: T,
  prev: F extends true ? T | undefined : T,
  r: IReactionPublic,
) => void;

export class Store {
  private reactionsDisposers: IReactionDisposer[] = [];

  dispose() {
    this.reactionsDisposers.forEach((disposer) => disposer());
  }

  protected reaction<T, F extends boolean = false>(
    expression: ReactionExpression<T>,
    effect: ReactionEffect<T, F>,
    options?: IReactionOptions<T, F>,
  ) {
    const disposer = reaction<T, F>(expression, effect, options);
    this.reactionsDisposers.push(disposer);

    return disposer;
  }
}
