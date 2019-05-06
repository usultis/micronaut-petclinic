/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.micronaut.petclinic;

import com.example.micronaut.petclinic.system.thymeleaf.PetClinicExpressionObjectDialect;
import com.sun.el.ExpressionFactoryImpl;
import io.micronaut.configuration.hibernate.jpa.condition.EntitiesInPackageCondition;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.jdbc.spring.HibernatePresenceCondition;
import io.micronaut.runtime.Micronaut;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.hql.internal.ast.HqlToken;
import org.hibernate.hql.internal.ast.tree.AggregateNode;
import org.hibernate.hql.internal.ast.tree.AssignmentSpecification;
import org.hibernate.hql.internal.ast.tree.BetweenOperatorNode;
import org.hibernate.hql.internal.ast.tree.BinaryArithmeticOperatorNode;
import org.hibernate.hql.internal.ast.tree.BinaryLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;
import org.hibernate.hql.internal.ast.tree.CastFunctionNode;
import org.hibernate.hql.internal.ast.tree.CollectionFunction;
import org.hibernate.hql.internal.ast.tree.ComponentJoin;
import org.hibernate.hql.internal.ast.tree.ConstructorNode;
import org.hibernate.hql.internal.ast.tree.CountNode;
import org.hibernate.hql.internal.ast.tree.DeleteStatement;
import org.hibernate.hql.internal.ast.tree.DotNode;
import org.hibernate.hql.internal.ast.tree.EntityJoinFromElement;
import org.hibernate.hql.internal.ast.tree.FromClause;
import org.hibernate.hql.internal.ast.tree.FromElement;
import org.hibernate.hql.internal.ast.tree.FromElementFactory;
import org.hibernate.hql.internal.ast.tree.FromReferenceNode;
import org.hibernate.hql.internal.ast.tree.HqlSqlWalkerNode;
import org.hibernate.hql.internal.ast.tree.IdentNode;
import org.hibernate.hql.internal.ast.tree.ImpliedFromElement;
import org.hibernate.hql.internal.ast.tree.InLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.IndexNode;
import org.hibernate.hql.internal.ast.tree.InsertStatement;
import org.hibernate.hql.internal.ast.tree.IntoClause;
import org.hibernate.hql.internal.ast.tree.IsNotNullLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.JavaConstantNode;
import org.hibernate.hql.internal.ast.tree.LiteralNode;
import org.hibernate.hql.internal.ast.tree.MapEntryNode;
import org.hibernate.hql.internal.ast.tree.MapKeyEntityFromElement;
import org.hibernate.hql.internal.ast.tree.MapKeyNode;
import org.hibernate.hql.internal.ast.tree.MapValueNode;
import org.hibernate.hql.internal.ast.tree.MethodNode;
import org.hibernate.hql.internal.ast.tree.Node;
import org.hibernate.hql.internal.ast.tree.NullNode;
import org.hibernate.hql.internal.ast.tree.OrderByClause;
import org.hibernate.hql.internal.ast.tree.ParameterNode;
import org.hibernate.hql.internal.ast.tree.QueryNode;
import org.hibernate.hql.internal.ast.tree.ResultVariableRefNode;
import org.hibernate.hql.internal.ast.tree.SearchedCaseNode;
import org.hibernate.hql.internal.ast.tree.SelectClause;
import org.hibernate.hql.internal.ast.tree.SelectExpressionImpl;
import org.hibernate.hql.internal.ast.tree.SelectExpressionList;
import org.hibernate.hql.internal.ast.tree.SimpleCaseNode;
import org.hibernate.hql.internal.ast.tree.SqlFragment;
import org.hibernate.hql.internal.ast.tree.SqlNode;
import org.hibernate.hql.internal.ast.tree.UnaryArithmeticNode;
import org.hibernate.hql.internal.ast.tree.UnaryLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.UpdateStatement;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.persister.collection.BasicCollectionPersister;
import org.hibernate.persister.collection.OneToManyPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.thymeleaf.extras.java8time.expression.Temporals;
import org.thymeleaf.standard.expression.AdditionExpression;
import org.thymeleaf.standard.expression.AdditionSubtractionExpression;
import org.thymeleaf.standard.expression.AndExpression;
import org.thymeleaf.standard.expression.DivisionExpression;
import org.thymeleaf.standard.expression.EqualsExpression;
import org.thymeleaf.standard.expression.EqualsNotEqualsExpression;
import org.thymeleaf.standard.expression.GreaterLesserExpression;
import org.thymeleaf.standard.expression.GreaterOrEqualToExpression;
import org.thymeleaf.standard.expression.GreaterThanExpression;
import org.thymeleaf.standard.expression.LessOrEqualToExpression;
import org.thymeleaf.standard.expression.LessThanExpression;
import org.thymeleaf.standard.expression.MultiplicationDivisionRemainderExpression;
import org.thymeleaf.standard.expression.MultiplicationExpression;
import org.thymeleaf.standard.expression.NotEqualsExpression;
import org.thymeleaf.standard.expression.OrExpression;
import org.thymeleaf.standard.expression.RemainderExpression;
import org.thymeleaf.standard.expression.SubtractionExpression;

/**
 * PetClinic Micronaut Application.
 *
 * @author Dave Syer
 * @author Mitz Shiiba
 */
@TypeHint(value = {
    // Hibernate
    HibernatePresenceCondition.class,
    EntitiesInPackageCondition.class,
    IdentityGenerator.class,
    SingleTableEntityPersister.class,
    EntityMetamodel.class,
    PojoEntityTuplizer.class,
    BasicCollectionPersister.class,
    SpringSessionContext.class,
    HqlToken.class,
    OneToManyPersister.class,
    // Hibernate AST
    AggregateNode.class,
    AssignmentSpecification.class,
    BetweenOperatorNode.class,
    BinaryArithmeticOperatorNode.class,
    BinaryLogicOperatorNode.class,
    BooleanLiteralNode.class,
    CastFunctionNode.class,
    CollectionFunction.class,
    ComponentJoin.class,
    ConstructorNode.class,
    CountNode.class,
    DeleteStatement.class,
    DotNode.class,
    EntityJoinFromElement.class,
    FromClause.class,
    FromElement.class,
    FromElementFactory.class,
    FromReferenceNode.class,
    HqlSqlWalkerNode.class,
    IdentNode.class,
    ImpliedFromElement.class,
    IndexNode.class,
    InLogicOperatorNode.class,
    InsertStatement.class,
    IntoClause.class,
    IsNotNullLogicOperatorNode.class,
    IsNullLogicOperatorNode.class,
    JavaConstantNode.class,
    LiteralNode.class,
    MapEntryNode.class,
    MapKeyEntityFromElement.class,
    MapKeyNode.class,
    MapValueNode.class,
    MethodNode.class,
    Node.class,
    NullNode.class,
    OrderByClause.class,
    ParameterNode.class,
    QueryNode.class,
    ResultVariableRefNode.class,
    SearchedCaseNode.class,
    SelectClause.class,
    SelectExpressionImpl.class,
    SelectExpressionList.class,
    SimpleCaseNode.class,
    SqlFragment.class,
    SqlNode.class,
    UnaryArithmeticNode.class,
    UnaryLogicOperatorNode.class,
    UpdateStatement.class,
    // Thymeleaf
    MultiplicationDivisionRemainderExpression.class,
    MultiplicationExpression.class,
    RemainderExpression.class,
    DivisionExpression.class,
    AndExpression.class,
    AdditionSubtractionExpression.class,
    AdditionExpression.class,
    SubtractionExpression.class,
    GreaterLesserExpression.class,
    LessOrEqualToExpression.class,
    GreaterOrEqualToExpression.class,
    LessThanExpression.class,
    GreaterThanExpression.class,
    EqualsNotEqualsExpression.class,
    EqualsExpression.class,
    NotEqualsExpression.class,
    OrExpression.class,
    Temporals.class,
    PetClinicExpressionObjectDialect.Fields.class,
    // Others
    ExpressionFactoryImpl.class,
    ImplicitNamingStrategyJpaCompliantImpl.class,
    PostgreSQL95Dialect.class
}, accessType = {TypeHint.AccessType.ALL_PUBLIC})
public class PetClinicApplication {

    public static void main(String[] args) {
        Micronaut.run(PetClinicApplication.class, args);
    }

}
